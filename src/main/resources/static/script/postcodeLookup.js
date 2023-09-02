
const postcodeInput = document.querySelector("#postcode");
const selectElement = document.getElementById('addresses');
const hiddenLatitude = document.getElementById('latitude');
const hiddenLongitude = document.getElementById('longitude');
const APIKey = postcodeAPI;
const headers = new Headers({
    'Key': APIKey
});


const options = {
    method: 'GET',
    headers: headers
};

postcodeInput.addEventListener("blur", () => {
    if (postcodeInput.checkValidity()) {
        loadAddresses(postcodeInput.value);
    }
});

selectElement.addEventListener("change", (evt)=>{
   const selectedAddress = evt.target.value;
   const locations = JSON.parse(selectedAddress.substring(selectedAddress.lastIndexOf('[')));
   hiddenLatitude.value = locations[0];
   hiddenLongitude.value = locations[1];
   selectElement.innerHTML = `<option value="${evt.target.value}">${evt.target.value}</option>`;
});

// Fetch the address data
const loadAddresses = (postcode) => {
    const apiURL = `https://api.easypostcodes.com/addresses/${postcode}`
    const progressBar = document.getElementById('progressBar');

// Fetch data
    fetch(apiURL, options)
        .then(response => response.json())
        .then(addresses => {
            // Populate the select box with options
            for (let i = 0; i < addresses.length; i++){
                const optionElement = document.createElement('option');
                const description = `${addresses[i].envelopeAddress.summaryLine},[${addresses[i].latitude}, ${addresses[i].longitude}]`;
                optionElement.value = description;
                optionElement.textContent = description;
                selectElement.appendChild(optionElement);
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });

};

