// const slides = document.querySelector('.carousel-slides');
// const prevButton = document.getElementById('prev');
// const nextButton = document.getElementById('next');

// let currentIndex = 0;

// function updateCarousel() {
//   const slideWidth = slides.children[0].offsetWidth;
//   slides.style.transform = `translateX(-${currentIndex * slideWidth}px)`;
// }

// prevButton.addEventListener('click', () => {
//   currentIndex = (currentIndex === 0) ? slides.children.length - 1 : currentIndex - 1;
//   updateCarousel();
// });

// nextButton.addEventListener('click', () => {
//   currentIndex = (currentIndex + 1) % slides.children.length;
//   updateCarousel();
// });

const slides = document.querySelector('.carousel-slides');
const prevButton = document.getElementById('prev');
const nextButton = document.getElementById('next');

let currentIndex = 0;
const intervalTime = 3000; // Time in milliseconds for each slide (3 seconds)
let autoplayInterval;

// Function to update the carousel position
function updateCarousel() {
  const slideWidth = slides.children[0].offsetWidth;
  slides.style.transform = `translateX(-${currentIndex * slideWidth}px)`;
}

// Move to the next slide
function nextSlide() {
  currentIndex = (currentIndex + 1) % slides.children.length;
  updateCarousel();
}

// Move to the previous slide
function prevSlide() {
  currentIndex = (currentIndex === 0) ? slides.children.length - 1 : currentIndex - 1;
  updateCarousel();
}

// Start autoplay
function startAutoplay() {
  autoplayInterval = setInterval(nextSlide, intervalTime);
}

// Stop autoplay
function stopAutoplay() {
  clearInterval(autoplayInterval);
}

// Add event listeners for buttons
prevButton.addEventListener('click', () => {
  stopAutoplay();
  prevSlide();
  startAutoplay(); // Restart autoplay after manual navigation
});

nextButton.addEventListener('click', () => {
  stopAutoplay();
  nextSlide();
  startAutoplay(); // Restart autoplay after manual navigation
});

// Initialize the carousel
updateCarousel();
startAutoplay();
