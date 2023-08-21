package com.team1.qrnavigationproject.controller;

import com.team1.qrnavigationproject.model.*;
import com.team1.qrnavigationproject.response.CustomException;
import com.team1.qrnavigationproject.service.QRCodeService;
import com.team1.qrnavigationproject.service.SpaceService;
import com.team1.qrnavigationproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.team1.qrnavigationproject.configuration.QRNavigationPaths.*;

@Controller
public class QRCodeController {

    private SpaceService spaceService;
    private QRCodeService qrCodeService;

    private UserService userService;

    @Autowired
    public void setSpaceService(SpaceService spaceService) {
        this.spaceService = spaceService;
    }

    @Autowired
    public void setQrCodeService(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private final static Logger LOGGER = Logger.getLogger(QRCodeController.class.getSimpleName());

    @GetMapping(ADMIN_QRCODES)
    public String viewQRCodes(Model model, Authentication auth) {
        //Access the currently authenticated admin/user
        Optional<User> currentAdmin = requestCurrentUser(auth);
        if (currentAdmin.isPresent()) {
            //pass a list of qrcodes and spaces managed by this admin
            User admin = currentAdmin.get();
            List<QRCode> managedQRCodes = findOrganizationManagedQRCodes(auth);
            List<Space> managedSpaces = findOrganizationManagedSpaces(admin);
            model.addAttribute("qrcodes", managedQRCodes);
            model.addAttribute("spaces", managedSpaces);
        } else {
            model.addAttribute("error", "Error (%d):Not Authorized".formatted(HttpStatus.UNAUTHORIZED.value()));
            return "redirect:" + LOGIN_ERROR;
        }
        return "qrcodes";
    }

    @GetMapping(ADMIN_QRCODES_GENERATE)
    public ModelAndView viewQRCodeGenerationPage(Authentication auth, ModelAndView modelAndView) {
        //Access the currently authenticated user
        Optional<User> currentAdmin = requestCurrentUser(auth);
        currentAdmin.ifPresentOrElse(admin -> {
            List<Space> spaces = findOrganizationManagedSpaces(admin);
            //pass these spaces to the view (qrcodeGeneration.html page)
            modelAndView.addObject("spaces", spaces)
                    .setViewName("qrcodeGeneration");
        }, () -> modelAndView
                .addObject("error", "Error (%d): Not Authorized".formatted(HttpStatus.UNAUTHORIZED.value()))
                .setViewName("redirect:" + LOGIN_ERROR));
        return modelAndView;
    }

    @PostMapping(ADMIN_QRCODES_GENERATE_PROCESS)
    public String processQRGeneration(
            @RequestParam(name = "spaceId") String spaceIdParam,
            @RequestParam(name = "subSpaceId") String subSpaceIdParam,
            @Valid @ModelAttribute QRCode qrcode,
            Authentication auth,
            HttpServletRequest request,
            ModelAndView mav,
            Model model,
            RedirectAttributes redirectAttributes) throws Exception {
        User admin;
        Space space;
        SubSpace subspace;
        int spaceId, subSpaceId;
        //Access the currently authenticated admin
        Optional<User> unconfirmedAdmin = requestCurrentUser(auth);
        //set the name of the page
        mav.setViewName("qrcodeGeneration");
        LOGGER.log(Level.INFO, "Verifying the current authenticated Admin to generate a QRCode for spaces");
        if (unconfirmedAdmin.isEmpty()) {
            //redirect back to the login page
            redirectAttributes.addFlashAttribute("error", "Error (%d):You are unauthorized".formatted(HttpStatus.UNAUTHORIZED.value()));
            LOGGER.log(Level.SEVERE, "Redirecting anonymous user to Login page, admin information was not retrieved");
            return "redirect:/" + LOGIN_ERROR;
        }
        LOGGER.log(Level.INFO, "Verifying if user has selected a space");
        if (Integer.parseInt(spaceIdParam) < 1){
            //redirect and prompt user to select a space
            redirectAttributes.addFlashAttribute("error", "Error (%d): A space has not been selected".formatted(HttpStatus.BAD_REQUEST.value()));
            LOGGER.log(Level.SEVERE, "Redirecting admin to select a space");
            return "redirect:%s".formatted(ADMIN_QRCODES_GENERATE);
        }
        LOGGER.log(Level.INFO, "Verifying if user has selected a subspace within a space");
        if(Integer.parseInt(subSpaceIdParam) < 1){
            //redirect and prompt user to select a subspace within a space
            redirectAttributes.addFlashAttribute("error", "Error (%d): A subspace has not been selected".formatted(HttpStatus.BAD_REQUEST.value()));
            LOGGER.log(Level.SEVERE, "Redirecting admin to select a subspace within a space");
            return "redirect:%s".formatted(ADMIN_QRCODES_GENERATE);
        }
        spaceId = Integer.parseInt(spaceIdParam);
        subSpaceId = Integer.parseInt(subSpaceIdParam);
        admin = unconfirmedAdmin.get();
        try {
            LOGGER.log(Level.INFO, "Authenticated Admin Retrieved");
            space = findOrganizationManagedSpaces(admin)
                    .stream()
                    .filter(s -> s.getId() == spaceId)
                    .findFirst()
                    .orElseThrow();

            LOGGER.log(Level.INFO, "Trying to retrieve a space: %s".formatted(space.getName()));

            subspace = space
                    .getSubSpaces()
                    .stream()
                    .filter(subSpace -> subSpace.getId() == subSpaceId)
                    .findFirst()
                    .orElseThrow();

            LOGGER.log(Level.INFO, "Trying to retrieve a subspace: %s".formatted(subspace.getName()));
            //set the space and subspaces
            LOGGER.log(Level.INFO, "Preparing QR code data for  %s and %s".formatted(space.getName(), subspace.getName()));
            qrcode.setSpace(space);
            qrcode.setSubSpace(subspace);
            //Create a page URL to encode in the qr code
            String pageURL = makePageURL(request, admin, space, subspace);
            LOGGER.log(Level.INFO, "Finished preparing page URL for the QR code to link %s to %s".formatted(space.getName(), subspace.getName()));
            //set the URL to the qrcode object
            qrcode.setPageURL(pageURL);
            //set the creation date
            qrcode.setCreatedAt(LocalDateTime.now());
            //save the qr code to the database
            QRCode savedQRCode = qrCodeService.save(qrcode);
            //pass the saved qr code information to the view for downloads at subsequent times
            if (savedQRCode != null) {
                model.addAttribute("spaces", space);
                model.addAttribute("qrcode", savedQRCode);
                redirectAttributes.addFlashAttribute("success", "Success (%d):QR code %d generated"
                        .formatted(HttpStatus.OK.value(), savedQRCode.getId()));
                LOGGER.log(Level.INFO, "Saved QRCode for %s, passing information to %s".formatted(subspace.getName(), mav.getViewName()));
                LOGGER.log(Level.SEVERE, "Generated QRCode %d for %s".formatted(savedQRCode.getId(), subspace.getName()));
            } else {
                LOGGER.log(Level.SEVERE, "Failed to Save QRCode for %s".formatted(subspace.getName()));
                redirectAttributes.addFlashAttribute("error", "Error (%d):Failed to generate QR code"
                        .formatted(HttpStatus.BAD_REQUEST.value()));
            }
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, "Failed to Save QRCode. See Details below:\n%s".formatted(e.getMessage()));
            redirectAttributes.addFlashAttribute("error", "Error (%d): %s".formatted(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
        return "redirect:%s".formatted(ADMIN_QRCODES_GENERATE);
    }

    @GetMapping(ADMIN_QRCODES_UPDATE + "/{id}")
    public ModelAndView viewToUpdateQRCodeData(
            @PathVariable int id,
            ModelAndView mav) {

        LOGGER.log(Level.INFO, "Fetching details of QRCode %d for update".formatted(id));
        Optional.of(qrCodeService.findQRCodeById(id))
                .ifPresentOrElse(qrCode -> {
                    //pass the qrcode data to the qrcode update page
                    mav.addObject("qrcode", qrCode);
                    mav.setViewName("qrcodeUpdate");
                    LOGGER.log(Level.INFO, "Passing details of QRCode %d for to %s".formatted(id, mav.getViewName()));
                }, () -> {
                    mav.addObject("error", "Error (%d): QR data not found"
                            .formatted(HttpStatus.NOT_FOUND.value()));
                    mav.setViewName("qrcodeUpdate");
                    LOGGER.log(Level.SEVERE, "Failed to Fetch details of QRCode %d for update".formatted(id));
                });
        return mav;
    }

    @PostMapping(ADMIN_QRCODES_UPDATE_PROCESS)
    public String processUpdate(@ModelAttribute QRCode qrFromFormData, ModelAndView mav, Model model, RedirectAttributes redirectAttributes) {
        if (qrFromFormData.getDescription().isBlank()) {
            LOGGER.log(Level.WARNING, "Trying to Update QRCode %d with blank description.".formatted(qrFromFormData.getId()));
            redirectAttributes.addFlashAttribute("warning", "Warning (%d): Trying to submit blank description".formatted(HttpStatus.BAD_REQUEST.value()));
            return "redirect:%s/%d".formatted(ADMIN_QRCODES_UPDATE, qrFromFormData.getId());
        }
        Optional.of(qrCodeService.findQRCodeById(qrFromFormData.getId())).ifPresentOrElse(qrCode -> {
            //update description and save changes
            LOGGER.log(Level.INFO, "Updating information about QRCode %d for update".formatted(qrCode.getId()));
            mav.setViewName("qrcodeUpdate");
            qrCode.setDescription(qrFromFormData.getDescription());
            model.addAttribute("qrcode", qrCodeService.patchQRCode(qrCode));
            LOGGER.log(Level.INFO, "Updated QRCode %d. Passing information to %s".formatted(qrCode.getId(), mav.getViewName()));
            redirectAttributes.addFlashAttribute("success", "Success (%d): QR code updated successfully".formatted(HttpStatus.OK.value()));
        }, () -> {
            redirectAttributes.addFlashAttribute("error", "Error (%d): Failed to update QR code data");
            LOGGER.log(Level.SEVERE, "Failed to update a QRCode.Redirecting admin to %s".formatted(mav.getViewName()));
        });
        return "redirect:%s/%d".formatted(ADMIN_QRCODES_UPDATE, qrFromFormData.getId());

    }

    @PostMapping(ADMIN_QRCODES_DOWNLOAD)
    public ResponseEntity<?> download(
            @RequestParam(name = "spaceId", defaultValue = "0") int spaceId,
            @RequestParam(name = "subSpaceId", defaultValue = "0") int subSpaceId,
            ModelAndView modelAndView) {
        Optional<QRCode> qrToDownload;
        qrToDownload = qrCodeService.findQRCodeBySpaceIdAndSubspaceId(spaceId, subSpaceId);
        try {
            return qrToDownload.map(qrCode -> {
                //download the qr code
                InputStreamResource resource = qrCodeService.download(qrCode.getId());
                String imageURL = qrCode.getImageURL();
                String fileName = imageURL.substring(imageURL.lastIndexOf("/") + 1);
                modelAndView.addObject("success", "Success (%d): Downloaded QRCode %d".formatted(HttpStatus.OK.value(),
                                qrToDownload.get().getId()))
                        .setViewName("redirect:/admin/qrcodes/generate");

                return ResponseEntity.ok()
                        .header("Content-type", "application/octet-stream")
                        .header("content-disposition", "attachment; filename=%s".formatted(fileName))
                        .body(resource);
            }).orElseThrow();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to download QRCode. See details below:\n%s".formatted(e.getMessage()));
            modelAndView.addObject("error", "Error (%d): Downloaded Failed".formatted(HttpStatus.NO_CONTENT.value()));
            modelAndView.setViewName("qrcodeGeneration");
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(ADMIN_QRCODES)
    public String deleteQRCode(@RequestParam("id") int id, Model model, RedirectAttributes redirectAttributes, Authentication auth) {
        //check if the qr code record exists
        Optional.of(qrCodeService.findQRCodeById(id))
                .ifPresentOrElse(
                        qrCode -> {

                            if (auth != null && auth.isAuthenticated()) {
                                try {
                                    qrCodeService.deleteById(id);
                                } catch (Exception e) {
                                    throw new CustomException(HttpStatus.NOT_FOUND);
                                }
                                redirectAttributes.addFlashAttribute("success", "Success (%d): QR Code %d deleted".formatted(HttpStatus.OK.value(), id));
                                model.addAttribute("qrcodes", findOrganizationManagedQRCodes(auth));
                            }
                        },
                        () -> redirectAttributes.addFlashAttribute("error", "Error (%d): Failed to delete this item".formatted(HttpStatus.NOT_FOUND.value()))
                );
        return "redirect:%s".formatted(ADMIN_QRCODES);

    }

    private String makePageURL(HttpServletRequest request, User admin, Space space, SubSpace subspace) {
        LOGGER.log(Level.INFO, "Creating page URL for the QR code to link %s to %s".formatted(space.getName(), subspace.getName()));
        UriTemplate uriTemplate = new UriTemplate(
                //pass URL template that will be encoded in the qr code
                ("%s://%s:%d" + QRCODE_PAGE_URL).formatted(
                        request.getScheme(),
                        request.getServerName(),
                        request.getServerPort()
                )
        );
        Map<String, String> mapOfPathVariables = Map.of(
                "organization", admin.getOrganization().getName(),
                "space", space.getName(),
                "subspace", subspace.getName()
        );
        //remove all whitespaces
        mapOfPathVariables.values().forEach(value -> value.replaceAll("\u0020", ""));
        LOGGER.log(Level.INFO, "Finishing page URL for the QR code to link %s to %s".formatted(space.getName(), subspace.getName()));
        return uriTemplate.expand(mapOfPathVariables).toString();
    }

    private List<Space> findOrganizationManagedSpaces(User admin) {
        LOGGER.log(Level.INFO, "Retrieving Organization Managed spaces");
        return spaceService
                .getAllSpaces()
                .stream()
                .filter(space -> space.getOrganization().getId() == admin.getOrganization().getId())
                .toList();
    }

    private List<QRCode> findOrganizationManagedQRCodes(Authentication auth) {
        LOGGER.log(Level.INFO, "Retrieving Organization Managed QRCodes");
        return qrCodeService
                .findQRCodesByOrganizationId(requestCurrentUser(auth)
                        .orElseThrow(() -> new CustomException(HttpStatus.UNAUTHORIZED))
                        .getOrganization()
                        .getId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND));

    }

    private Optional<User> requestCurrentUser(Authentication auth) {
        Optional<User> currentAdmin = Optional.empty();
        if (auth != null && auth.isAuthenticated()) {
            currentAdmin = userService.findUserByUsername(auth.getName());
        }
        return currentAdmin;
    }

}
