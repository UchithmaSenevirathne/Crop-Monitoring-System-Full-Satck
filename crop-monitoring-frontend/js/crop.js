const apiUrlCrop = "http://localhost:8080/crop";

// const API_BASE_URL_FIELD = "http://localhost:8080/field"

let alertTypeCrop = "Crop saved successfully!";

// Utility to convert file to Base64
const getBase64Crop = (file) =>
  new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.onload = () => resolve(reader.result.split(",")[1]);
    reader.onerror = (error) => reject(error);
    reader.readAsDataURL(file);
  });


  // Fetch and populate categories and fields in dropdowns
// function loadCategoriesAndFields() {
//   fetch(`${apiUrlCrop}/category`)
//       .then(response => response.json())
//       .then(categories => {
//           const categoryDropdown = document.getElementById("category");
//           console.log(categories);
//           categoryDropdown.innerHTML = categories.map(cat => `<option value="${cat}">${cat}</option>`).join('');
//       });

//   fetch(`${apiUrl}/all_fields`) // Replace with actual fields endpoint
//       .then(response => response.json())
//       .then(fields => {
//           const fieldDropdown = document.getElementById("fields");
//           console.log(fields)
//           fieldDropdown.innerHTML = fields.map(field => `<option value="${field.name}">${field.name}</option>`).join('');
//       });
// }

function loadCategoriesAndFields() {
  // Load categories
  fetch(`${apiUrlCrop}/category`)
    .then(response => response.json())
    .then(categories => {
      const categoryDropdown = document.getElementById("category");
      if (!categoryDropdown) {
        console.error("Category dropdown not found");
        return;
      }
      console.log("Categories:", categories);

      // Check if categories is an array of strings or objects
      categoryDropdown.innerHTML = categories.map(cat =>
        typeof cat === "string" 
          ? `<option value="${cat}">${cat}</option>` 
          : `<option value="${cat.id || cat.name}">${cat.name}</option>`
      ).join('');
    })
    .catch(error => console.error("Error loading categories:", error));

  // Load fields
  fetch(`${apiUrl}/all_fields`)
    .then(response => response.json())
    .then(fieldNames => {
      const fieldDropdown = document.getElementById("fields");
      if (!fieldDropdown) {
        console.error("Field dropdown not found");
        return;
      }
      console.log("Fields:", fieldNames);

      // Check if fields have a `name` property
      fieldDropdown.innerHTML = fieldNames.map(field => 
        `<option value="${field.fieldCode}">${field.fieldName}</option>`
      ).join('');
    })
    .catch(error => console.error("Error loading fields:", error));
}

// Function to load all fields and populate the table
async function loadCrops() {
  try {
    const response = await fetch(`${apiUrlCrop}/all_crops`);
    const crops = await response.json();

    const tableBody = document.querySelector("tbody");
    if (!tableBody) {
      console.log("Table body not found. Skipping Crop population.");
      return;
    }
    
    tableBody.innerHTML = ""; // Clear existing rows

    crops.forEach((crop) => {
      const row = `
        <tr class="border-t border-gray-200 hover:bg-gray-50">
          <td class="px-4 py-2 text-gray-700">${crop.cropCode}</td>
          <td class="px-4 py-2 text-gray-700">${crop.cropCommonName}</td>
          <td class="px-4 py-2 text-gray-700">${crop.cropScientificName}</td>
          <td class="px-4 py-2 text-gray-700">${crop.category}</td>
          <td class="px-4 py-2 text-gray-700">${crop.cropSeason}</td>
          <td class="px-4 py-2 text-gray-700">${crop.fieldCode}</td>
          <td class="px-4 py-2 text-gray-700">
            <img src="data:image/png;base64,${crop.cropImage}" class="w-16 h-16 rounded" alt="Crop Image" />
          </td>
          <td class="px-4 py-2 text-center">
            <button class="text-blue-500 hover:text-blue-700 mx-2" onclick="editCrop(${crop.cropCode})">
              <i class="fas fa-edit"></i>
            </button>
            <button class="text-red-500 hover:text-red-700 mx-2" onclick="deleteCrop(${crop.cropCode})">
              <i class="fas fa-trash-alt"></i>
            </button>
          </td>
        </tr>`;
      tableBody.insertAdjacentHTML("beforeend", row);
    });
  } catch (error) {
    console.error("Error loading crops:", error);
  }
}

// Save or update field
async function saveOrUpdateCrop() {
  const cropCommonName = document.querySelector('input[id="cropCommonName"]').value;
  const cropScientificName = document.querySelector('input[id="cropScientificName"]').value;
  const cropImageFile = document.querySelector("#image-upload-crop").files[0];
  const category = document.getElementById('category').value;
  const cropSeason = document.querySelector('input[name="cropSeason"]:checked').value;
  const fieldCode = document.getElementById('fields').value;
  const button = document.getElementById("btnCrop");

  try {
    const cropImage = cropImageFile ? await getBase64Crop(cropImageFile) : null;

    console.log(fieldCode);

    const method = button.dataset.mode === "edit" ? "PUT" : "POST";
    alertTypeCrop = button.dataset.mode === "edit" ? "Crop updated successfully!" : "Crop saved successfully!"
    const cropCode = button.dataset.cropCode || "";
    const url = method === "POST" ? apiUrlCrop : `${apiUrlCrop}/update/${cropCode}`;
    const formData = new FormData();

    formData.append("cropCommonName", cropCommonName);
    formData.append("cropScientificName", cropScientificName);
    if(cropImage) formData.append("cropImage", cropImageFile);
    formData.append("category", category);
    formData.append("cropSeason", cropSeason);
    formData.append("fieldCode", fieldCode);

    await fetch(url, { method, body: formData });
    alert(alertTypeCrop);
    resetFormCrop();
    loadCrops();
  } catch (error) {
    console.error("Error saving crop:", error);
  }
}

// Edit field
// async function editCrop(cropCode) {
//   console.log(cropCode)
//   try {
//     const response = await fetch(`${apiUrlCrop}/get/${cropCode}`);
//     const crop = await response.json();

//     document.querySelector('input[id="cropCommonName"]').value = crop.cropCommonName;
//     document.querySelector('input[id="cropScientificName"]').value = crop.cropScientificName;
//     document.getElementById('category').value = crop.category;
//     document.querySelector('input[name="cropSeason"]:checked').value = crop.cropSeason;
//     document.querySelector('input[id="fields"]').value = crop.fieldCode;
//     document.querySelector("#image-upload-crop").files[0] = crop.cropImage;

//     const button = document.getElementById("btnCrop");
//     button.textContent = "Update Crop";
//     button.dataset.mode = "edit";
//     button.dataset.cropCode = cropCode;
//   } catch (error) {
//     console.error("Error editing Crop:", error);
//   }
// }

async function editCrop(cropCode) {
  try {
    const response = await fetch(`${apiUrlCrop}/get/${cropCode}`);
    const crop = await response.json();

    // Set text inputs
    document.querySelector('input[id="cropCommonName"]').value = crop.cropCommonName;
    document.querySelector('input[id="cropScientificName"]').value = crop.cropScientificName;

    // Set select fields
    const categoryDropdown = document.getElementById('category');
    if (categoryDropdown) {
      categoryDropdown.value = crop.category;
    }

    const fieldsDropdown = document.getElementById('fields');
    if (fieldsDropdown) {
      fieldsDropdown.value = crop.fieldCode;
    }

    // Set radio button for crop season
    const seasonRadios = document.querySelectorAll('input[name="cropSeason"]');
    seasonRadios.forEach(radio => {
      if (radio.value === crop.cropSeason) {
        radio.checked = true;
      }
    });

    // Handle image 
    const imageUpload = document.getElementById('image-upload-crop');
    if (crop.cropImage) {
      // Convert base64 to File object
      const byteCharacters = atob(crop.cropImage);
      const byteNumbers = new Array(byteCharacters.length);
      for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
      }
      const byteArray = new Uint8Array(byteNumbers);
      const blob = new Blob([byteArray], { type: 'image/png' }); // Adjust mime type if needed
      
      // Create a File object
      const file = new File([blob], 'crop-image.png', { type: 'image/png' });
      
      // Create a DataTransfer object to set files
      const dataTransfer = new DataTransfer();
      dataTransfer.items.add(file);
      imageUpload.files = dataTransfer.files;

      // Optional: Show image preview
      const preview = document.getElementById('image-preview-crop');
      if (preview) {
        preview.src = `data:image/png;base64,${crop.cropImage}`;
      }
    }

    const button = document.getElementById("btnCrop");
    button.textContent = "Update Crop";
    button.dataset.mode = "edit";
    button.dataset.cropCode = cropCode;

  } catch (error) {
    console.error("Error editing Crop:", error);
  }
}

// Delete field
async function deleteCrop(cropCode) {
  if (!confirm("Are you sure to delete this data?")) return;

  try {
    await fetch(`${apiUrlCrop}/delete/${cropCode}`, { method: "DELETE" });
    alert("Crop deleted successfully!");
    loadCrops();
  } catch (error) {
    console.error("Error deleting crop:", error);
  }
}

// Reset form to default state
function resetFormCrop() {
  document.querySelector('input[placeholder="BG-35 Rice"]').value = "";
  document.querySelector('input[placeholder="Oryza sativa"]').value = "";
  
  document.querySelector("#image-upload-crop").value = "";
  
  const button = document.querySelector("button");
  button.textContent = "Add Crop";
  button.dataset.mode = "add";
  button.removeAttribute("data-crop-code");
}

// Load fields on page load
document.addEventListener("DOMContentLoaded", () => {

  loadCategoriesAndFields();
  // Only run these if the elements exist
  const tableBody = document.querySelector("tbody");
  if (tableBody) {
    loadCrops();
  }

  const saveButtonCrop = document.getElementById("btnCrop");
  if (saveButtonCrop) {
    saveButtonCrop.addEventListener("click", saveOrUpdateCrop);
  } else {
    console.log("Save button not found. This might be okay if not on the Crop page.");
  }
});



// Attach preview functions to the window object to make them globally accessible
window.previewImageCrop = function(event) {
  const file = event.target.files[0]; // Get the selected file
  if (file) {
    const reader = new FileReader();
    reader.onload = function (e) {
      // Optional: If you want to show a preview, uncomment and add an img element with id='image-preview-field1'
      // const preview = document.getElementById('image-preview-field1');
      // preview.src = e.target.result;
      console.log("Crop image selected:", file.name);
    };
    reader.readAsDataURL(file); // Read the file as a Data URL
  }
}

function filterCropTable() {
  const searchValue = document.getElementById("searchCrop").value.toLowerCase();
  const table = document.getElementById("cropTable");
  const rows = table.querySelectorAll("tbody tr");

  rows.forEach((row) => {
    const cells = row.querySelectorAll("td");
    const cropCommonName = cells[1]?.textContent.toLowerCase() || "";
    const cropScientificName = cells[2]?.textContent.toLowerCase() || "";
    const category = cells[3]?.textContent.toLowerCase() || "";
    const cropSeason = cells[4]?.textContent.toLowerCase() || "";

    // Match search value with any of the fields
    if (
      cropCommonName.includes(searchValue) ||
      cropScientificName.includes(searchValue) ||
      category.includes(searchValue) ||
      cropSeason.includes(searchValue)
    ) {
      row.style.display = ""; // Show row
    } else {
      row.style.display = "none"; // Hide row
    }
  });
}
