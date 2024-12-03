// imageSelect.js
console.log("imageSelect.js is loaded");

document.addEventListener("DOMContentLoaded", () => {
  // Collect all image inputs on the page
  const imageInputs = document.querySelectorAll('input[type="file"]');
  console.log("........00.....")

  imageInputs.forEach((input) => {
    const imageId = input.id; // Unique input ID
    const previewId = `imagePreview${imageId.replace("imageInput", "")}`;
    const placeholderId = `placeholder${imageId.replace("imageInput", "")}`;

    input.addEventListener("change", (event) => {
      const file = event.target.files[0];
      const preview = document.getElementById(previewId);
      const placeholder = document.getElementById(placeholderId);
      console.log("........01.....")
      if (file) {
        const reader = new FileReader();
        console.log("........02.....")
        reader.onload = (e) => {
          preview.src = e.target.result;
          preview.classList.remove("hidden");
          placeholder.classList.add("hidden");
        };
        reader.readAsDataURL(file);
      } else {
        console.log("........03.....")
        // Reset preview and placeholder
        preview.src = "";
        preview.classList.add("hidden");
        placeholder.classList.remove("hidden");
      }
    });
  });
});


// function handleImageInput(imageInputId, imagePreviewId, placeholderId) {
//   const imageInput = document.getElementById(imageInputId);
//   const imagePreview = document.getElementById(imagePreviewId);
//   const placeholder = document.getElementById(placeholderId);

//   imageInput.addEventListener("change", () => {
//     const file = imageInput.files[0];
//     if (file) {
//       const reader = new FileReader();
//       reader.onload = () => {
//         imagePreview.src = reader.result;
//         imagePreview.classList.remove("hidden");
//         placeholder.classList.add("hidden");
//       };
//       reader.readAsDataURL(file);
//     } else {
//       imagePreview.src = "";
//       imagePreview.classList.add("hidden");
//       placeholder.classList.remove("hidden");
//     }
//   });
// }

// document.addEventListener("DOMContentLoaded", () => {
//   const pageType = document.body.dataset.page; // or classList.contains("crop-page")

//   if (pageType === "crop-page") {
//     handleImageInput("imageInput3", "imagePreview3", "placeholder3");
//   } else if (pageType === "log-page") {
//     handleImageInput("imageInput4", "imagePreview4", "placeholder4");
//   }  else if (pageType === "field-page") {
//     handleImageInput("imageInput1", "imagePreview1", "placeholder1");
//     handleImageInput("imageInput2", "imagePreview2", "placeholder2");  } 
// });

// Initialize the handlers for both image inputs
// handleImageInput("imageInput1", "imagePreview1", "placeholder1");
// handleImageInput("imageInput2", "imagePreview2", "placeholder2");
// handleImageInput("imageInput3", "imagePreview3", "placeholder3");
// handleImageInput("imageInput4", "imagePreview4", "placeholder4");
