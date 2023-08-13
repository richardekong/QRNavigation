//define organizational theme past from current authenticated admin

const theme = /*[[${organizationTheme}]]*/ {};

//function to style the header
const styleAdminHeaderTheme = (theTheme) => {
    //define element to style
    let adminHeader = document.querySelector("#adminHeader");
    let adminHeaderLogo = document.querySelector("#adminHeader-logo");
    let adminHeaderOrgName = document.querySelector("#adminHeader-org-name");
    let adminHeaderLogoutButton = document.querySelector("#adminHeader-org-logout-button");

    //set the style
    adminHeader.style.backgroundColor = `${theTheme.headerBackground}` ?? `${adminHeader.style.backgroundColor}`;
    adminHeaderLogo.src = `${theTheme.logoURL}` ?? adminHeaderLogo.src;
    adminHeaderOrgName.textContent = `${theTheme.name}` ?? adminHeaderOrgName.textContent;
    adminHeaderLogoutButton.style.backgroundColor = `${theTheme.headerBackground}` ?? adminHeaderLogoutButton.style.backgroundColor;

};

//function to restyle the admin navigation bar
const styleAdminNavBar = (theTheme) => {
    //define the element to style
    let adminNavBar = document.querySelector("#adminNavBar");

    //set the background color of the navigation bar
    adminNavBar.style.backgroundColor = `${theTheme.headerBackground}` ?? adminNavBar.style.backgroundColor;
};

//function to restyle the admin footer
const styleAdminFooter = (theTheme) => {
    //define the element to style
    let adminFooter = document.querySelector("#adminFooter");
    let adminFooterLogo = document.querySelector("#adminFooter-logo");
    let adminFooterOrgName = document.querySelector("#adminFooter-org-name");
    let adminFooterWebsiteLinks = document.querySelectorAll(".adminFooter-org-website-link");

    //set the background, logo, organization name and website of these elements defined above
    adminFooter.style.backgroundColor = `${theTheme.footerBackground}` ?? adminFooter.style.backgroundColor;
    adminFooterLogo.src = `${theTheme.logoURL}` ?? adminFooterLogo.src;
    adminFooterOrgName.textContent = `${theTheme.name}` ?? adminFooterOrgName.textContent;
    adminFooterWebsiteLinks.forEach(link => link.href = `${theTheme.websiteURL}` ?? link.href);
};

styleAdminHeaderTheme(theme);
styleAdminNavBar(theme);
styleAdminFooter(theme);