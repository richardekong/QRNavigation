package com.team1.qrnavigationproject.controller;

import com.team1.qrnavigationproject.model.QRCode;
import com.team1.qrnavigationproject.model.Space;
import com.team1.qrnavigationproject.model.SubSpace;
import com.team1.qrnavigationproject.model.User;
import com.team1.qrnavigationproject.response.CustomException;
import com.team1.qrnavigationproject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.team1.qrnavigationproject.configuration.QRNavigationPaths.*;

@Controller
public class QRCodeController {

    private SpaceService spaceService;

    private SubSpaceService subspaceService;

    private QRCodeService qrCodeService;

    private UserService userService;

    private OrganizationService organizationService;

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



    @Autowired
    public void setSubspaceService(SubSpaceService subspaceService) {
        this.subspaceService = subspaceService;
    }

    @Autowired
    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping(ADMIN_QRCODES)
    public ModelAndView viewQRCodes(ModelAndView modelAndView) {
        modelAndView.setViewName("qrcodes");
        return modelAndView;
    }

    @GetMapping(ADMIN_QRCODES_GENERATE)
    public ModelAndView viewQRCodeGenerationPage(Authentication auth, ModelAndView modelAndView)  {
        //Access the currently authenticated user
        Optional<User> currentAdmin = requestCurrentUser(auth);
        currentAdmin.ifPresentOrElse(admin -> {
            //Todo: I will refactor this code below to get spaces from the datasource as soon as the
            // assigned team member is done with implementing this feature
            List<Space> spaces = null;
            try {
                spaces = createDummySpaces(admin);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            //pass the space to the view (qrcodeGeneration.html page)
            modelAndView.addObject("spaces", spaces);
        }, () -> {
            modelAndView.setViewName("redirect:" + ADMIN_QRCODES_GENERATE);
            throw new CustomException("Not Authorized", HttpStatus.UNAUTHORIZED);
        });

        modelAndView.setViewName("qrcodeGeneration");
        return modelAndView;
    }

    @PostMapping(ADMIN_QRCODES_GENERATE_PROCESS)
    public ModelAndView processQRGeneration(
            @Valid @ModelAttribute QRCode qrcode,
            Authentication auth,
            HttpServletRequest request,
            ModelAndView modelAndView) throws Exception {
        //Access the currently authenticated admin
        Optional<User> unconfirmedAdmin = requestCurrentUser(auth);
        if (unconfirmedAdmin.isEmpty()) {
            //redirect back to the login page
            modelAndView.addObject("error", "Error (%d): Not Allowed".formatted(HttpStatus.UNAUTHORIZED.value()))
                    .setViewName("redirect:/" + LOGIN_ERROR);
            return modelAndView;
        }
        User admin = unconfirmedAdmin.get();
        //Todo: Will the space from the database latter retrieve from the
        Space dummySpace = createDummySpaces(admin)
                .stream()
                .filter(space -> space.getId() == qrcode.getSpaceId())
                .findFirst()
                .orElseThrow();

        SubSpace dummySubspace = dummySpace
                .getSubSpaces()
                .stream()
                .filter(subSpace -> subSpace.getId() == qrcode.getSubSpaceId())
                .findFirst()
                .orElseThrow();
        //Create a page URL to encode in the qr code
        String pageURL = makePageURL(request, admin, dummySpace, dummySubspace);
        //set the URL to the qrcode object
        qrcode.setPageURL(pageURL);
        //save the qr code to the database
        QRCode savedQRCode = qrCodeService.save(qrcode);
        //pass the saved qr code information to the view for downloads at subsequent times
        modelAndView.addObject(savedQRCode);
        modelAndView.setViewName("qrcodeGeneration");
        return modelAndView;
    }

    @GetMapping(ADMIN_QRCODES_UPDATE)
    public ModelAndView viewToUpdateQRCodeData(
            @ModelAttribute QRCode qrTOViewAndUpdate,
            ModelAndView mav) {
        mav.setViewName("qrcodeUpdate");
        return mav.addObject("qrCode", qrTOViewAndUpdate);
    }

    @PostMapping(ADMIN_QRCODES_UPDATE_PROCESS)
    public ModelAndView linkQRCodeToSpace(
            @ModelAttribute QRCode qrFromFormData,
            ModelAndView mav,
            Authentication auth,
            HttpServletRequest request) throws Exception {
        Optional<QRCode> optionalQrToUpdate;
        Optional<User> unconfirmedAdmin = requestCurrentUser(auth);
        if (unconfirmedAdmin.isEmpty()) {
            //redirect back to the login page
            mav.addObject("error", "Error (%d): Not Allowed".formatted(HttpStatus.UNAUTHORIZED.value()))
                    .setViewName("redirect:/" + LOGIN_ERROR);
            return mav;
        }
        if (qrFromFormData == null) {
            mav.addObject("error", "Error (%d): Failed to obtain QR data".formatted(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .setViewName("qrcodeUpdate");
            return mav;
        }
        optionalQrToUpdate = qrCodeService.findQRCodeBySpaceIdAndSubspaceId(qrFromFormData.getSpaceId(), qrFromFormData.getSubSpaceId());
        if (optionalQrToUpdate.isEmpty()) {

            //No qr code has been linked with this space and subspace. Hence change the page url and update accordingly
            //Todo: Will the space from the database latter retrieve from the
            Space dummySpace = createDummySpaces(unconfirmedAdmin.get())
                    .stream()
                    .filter(space -> space.getId() == qrFromFormData.getSpaceId())
                    .findFirst()
                    .orElseThrow();

            SubSpace dummySubspace = dummySpace
                    .getSubSpaces()
                    .stream()
                    .filter(subSpace -> subSpace.getId() == qrFromFormData.getSubSpaceId())
                    .findFirst()
                    .orElseThrow();

            String pageURL = makePageURL(request, unconfirmedAdmin.get(), dummySpace, dummySubspace);
            qrFromFormData.setPageURL(pageURL);
            mav.addObject("qrcode", qrCodeService.update(qrFromFormData))
                    .setViewName("qrcodeUpdate");
            return mav;
        }
        //There exists a qr code linked with these space and subspace
        if (qrFromFormData.getId() != optionalQrToUpdate.get().getId()) {
            //swap qr images before saving
            mav.addObject("qrcode", qrCodeService.linkQRToSPace(qrFromFormData))
                    .addObject("success", "Success (%d): QR code linked successfully".formatted(
                            HttpStatus.OK.value()
                    ))
                    .setViewName("qrcodeUpdate");
        } else {
            //just make subtle update
            mav.addObject("qrcode", qrCodeService.save(qrFromFormData))
                    .addObject("success", "Success (%d): QR code updated successfully".formatted(
                            HttpStatus.OK.value()
                    ))
                    .setViewName("qrcodeUpdate");
        }
        return mav;

    }

    @GetMapping("/admin/qrcodes/download")
    public ResponseEntity<Object> download(
            @ModelAttribute QRCode qrToDownload,
            ModelAndView modelAndView) {

        if (qrToDownload == null) {
            //flash an error message
            String msg = ("Error (%d): Error downloading QR code. " +
                    "\n Please Generate a QR code before downloading")
                    .formatted(HttpStatus.NOT_FOUND.value());
            modelAndView
                    .addObject("error", msg)
                    .setViewName("qrcodeGeneration");
            return new ResponseEntity<>(modelAndView, HttpStatus.BAD_REQUEST);
        }
        //download the qr code
        InputStreamResource resource = qrCodeService.download(qrToDownload.getId());

        return ResponseEntity.ok()
                .header("Content-type", "application/octet-stream")
                .header("content-disposition", "attachment; filename=%s".formatted(resource.getFilename()))
                .body(resource);

    }

    @DeleteMapping("/admin/qrcodes/{id}")
    public void deleteQRCode(@PathVariable int id, ModelAndView mav) {
        //check if the qr code record exists
        Optional.of(qrCodeService.findQRCodeById(id))
                .ifPresentOrElse(
                        qrCode -> qrCodeService.deleteById(id),
                        () -> mav.addObject(
                                "error",
                                "Error (%d): Failed to delete this item".formatted(HttpStatus.NOT_FOUND.value())
                        ).setViewName("redirect:/" + ADMIN_QRCODES));

    }

    private String makePageURL(HttpServletRequest request, User admin, Space dummySpace, SubSpace dummySubspace) {
        UriTemplate uriTemplate = new UriTemplate(
                //pass URL template that will be encoded in the qr code
                "%s://%s:%d/qrnavigation/{organization}/{space}/{subspace}".formatted(
                        request.getScheme(),
                        request.getServerName(),
                        request.getServerPort()
                )
        );
        Map<String, String> mapOfPathVariables = Map.of(
                "organization", admin.getOrganization().getName(),
                "space", dummySpace.getName(),
                "subspace", dummySubspace.getName()
        );

        return uriTemplate.expand(mapOfPathVariables).toString();
    }

    private Optional<User> requestCurrentUser(Authentication auth) {
        Optional<User> currentAdmin = Optional.empty();
        if (auth != null && auth.isAuthenticated()) {
            currentAdmin = userService.findUserByUsername(auth.getName());
        }
        return currentAdmin;
    }

    private List<Space> createDummySpaces(User admin) throws Exception{
        //Todo: Get list of spaces. Since there are no spaces in the datasource,
        //Todo: I will stick to a dummy list of spaces
//            List<Space> spaces = new LinkedList<>();
//            //Todo : make space 1
//            Location location = new Location(1, 51.045, -1.4543, null);
//            Address address = new Address(1, "That place", null, "CF24 4AG",
//                    admin.getOrganization());
//            Space abacws = new Space(
//                    1, "Abacws", "School of Computer Science Building", "[]",
//                    admin.getOrganization(), address.getId(), 1, null, null
//            );
//            SubSpace room102 = new SubSpace(1, "%s / 1.02".formatted(abacws.getName()),
//                    "", "", null, null, 2
//            );
//            SubSpace room101 = new SubSpace(2, "%s / 1.01".formatted(abacws.getName()),
//                    "", "", null, null, 2
//            );

//            Event event = new Event(
//                    1,
//                    "Open Day",
//                    "open Day Event",
//                    null,
//                    null,
//                    null,
//                    LocalDateTime.now().plusMinutes(3),
//                    LocalDateTime.now().plusMonths(3).plusHours(12),
//                    "[]"
//            );
//
//            admin.getOrganization().add(event);
//            room101.setEvent(event);
//            room102.setEvent(event);
//
//            abacws.setEvent(event);
//
//            abacws.add(room102);
//            abacws.add(room101);
//
////        Location savedLocation = locationService.saveLocation(location);
//
//            address.setLocation(location);
//
////        Address savedAddress = addressService.saveAddress(address);
//
//            abacws.setAddressId(address.getId());
//
////        Space savedSpace = spaceService.saveSpace(abacws);
//
//            spaces.add(abacws);
//
//            return spaces;

        var organization = admin.getOrganization();
        List<Space> spaces = spaceService.getAllSpaces();
//        spaces.forEach(organization::add);
        System.out.println(spaces);
//        admin.setOrganization(organizationService.update(organization));
//        userService.update(admin);

        //update spaces

        return spaces;
    }

}
