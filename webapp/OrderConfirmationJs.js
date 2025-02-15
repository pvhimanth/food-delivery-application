document.addEventListener("DOMContentLoaded", () => {
  const progressContainer = document.querySelector(".progress-container");
  const scooterContainer = document.querySelector(".scooter-container");
  const road = document.querySelector(".road");

  const startAnimation = () => {
    const containerWidth = progressContainer.offsetWidth;
    
    
    scooterContainer.style.left = `${containerWidth - 100}px`;
    
    scooterContainer.addEventListener("transitionend", () => {
      road.style.display = "none";
      scooterContainer.style.display = "none";
      
      const checkmark = document.createElement("div");
      checkmark.className = "checkmark-container";
      checkmark.innerHTML = `
        <div class="checkmark-circle">
          <div class="checkmark-check"></div>
        </div>
        <p class="success-text">Order Placed Successfully!</p>
      `;
      
      progressContainer.appendChild(checkmark);
    });
  };

  setTimeout(startAnimation, 300);
});