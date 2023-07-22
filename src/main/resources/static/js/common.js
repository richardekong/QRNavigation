// Get event by id
axios.get('/api/events/123')
    .then(function (response) {
        // Handle the response data and display it on the page
        document.getElementById("event-name").textContent = dateFormatter(response.data.eventName)

        createCarouselItems(response.data.images)

        document.getElementById("event-description").textContent = dateFormatter(response.data.eventDescription)

        document.getElementById("event-address").textContent = dateFormatter(response.data.address)
        document.getElementById("event-postal-code").textContent = dateFormatter(response.data.postalCode)

        document.getElementById("open-day-description").textContent = dateFormatter(response.data.openDayDescription)
        document.getElementById("event-start-time").textContent = dateFormatter(response.data.eventStartTime)
        document.getElementById("event-end-time").textContent = dateFormatter(response.data.eventEndTime)
        document.getElementById("event-duration").textContent = dateFormatter(response.data.duration)

        var dataDiv = document.getElementById('card-venues-values');
        var venuesArray = response.data.venues;

        venuesArray.forEach(function (venue) {
            var pElement = document.createElement('p');
            pElement.textContent = venue.name + " / " + venue.value;
            dataDiv.appendChild(pElement);
        });
    })
    .catch(function (error) {
        console.error('Error occurred:', error);
    });

// Date formatter
function dateFormatter(localDateTime) {
    return localDateTime.toLocaleString("en-US", {
        weekday: "short", // Short weekday name (e.g., "Wed")
        day: "2-digit",   // Day of the month with leading zeros (e.g., "26")
        month: "short",   // Short month name (e.g., "Jul")
        year: "numeric",  // Full year (e.g., "2023")
        hour: "numeric",  // Hours with leading zeros (e.g., "09")
        minute: "numeric", // Minutes with leading zeros (e.g., "30")
    });
}

// Event banners
function createCarouselItems(imagesArray) {
    const carouselInner = document.querySelector(".carousel-inner");

    imagesArray.forEach((imageUrl, index) => {
        const carouselItem = document.createElement("div");
        carouselItem.classList.add("carousel-item");

        const image = document.createElement("img");
        image.src = imageUrl;
        image.classList.add("d-block", "w-100");
        image.alt = `Image ${index + 1}`;

        // Set the first item as active
        if (index === 0) {
            carouselItem.classList.add("active");
        }

        carouselItem.appendChild(image);
        carouselInner.appendChild(carouselItem);
    });
}
