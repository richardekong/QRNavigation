

// Event banners
//This functionality is used in the contentPage.html
function createCarouselItems(imagesArray) {
    const carouselInner = document.querySelector(".carousel-inner");

    imagesArray.forEach((imageUrl, index) => {
        const carouselItem = document.createElement("div");
        carouselItem.classList.add("carousel-item");
        carouselItem.style.maxHeight = '25%';

        const image = document.createElement("img");
        image.src = imageUrl;
        image.classList.add("d-block", "w-100", "h-25");
        image.alt = `Image ${index + 1}`;

        // Set the first item as active
        if (index === 0) {
            carouselItem.classList.add("active");
        }

        carouselItem.appendChild(image);
        carouselInner.appendChild(carouselItem);
    });
}
