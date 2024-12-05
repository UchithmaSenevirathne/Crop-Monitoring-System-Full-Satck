const apiUrl = "http://localhost:8080/field";

let alertType = "Field saved successfully!";

// Utility to convert file to Base64
const getBase64 = (file) =>
  new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.onload = () => resolve(reader.result.split(",")[1]);
    reader.onerror = (error) => reject(error);
    reader.readAsDataURL(file);
  });

// Function to load all fields and populate the table
async function loadFields() {
  try {
    const response = await fetch(`${apiUrl}/all_fields`);
    const fields = await response.json();

    const tableBody = document.querySelector("tbody");
    if (!tableBody) {
      console.log("Table body not found. Skipping field population.");
      return;
    }
    
    tableBody.innerHTML = ""; // Clear existing rows

    fields.forEach((field) => {
      const row = `
        <tr class="border-t border-gray-200 hover:bg-gray-50">
          <td class="px-4 py-2 text-gray-700">${field.fieldCode}</td>
          <td class="px-4 py-2 text-gray-700">${field.fieldName}</td>
          <td class="px-4 py-2 text-gray-700">${field.fieldLocation}</td>
          <td class="px-4 py-2 text-gray-700">${field.extentSize}</td>
          <td class="px-4 py-2 text-gray-700">
            <img src="data:image/png;base64,${field.fieldImage1}" class="w-16 h-16 rounded" alt="Field Image 1" />
          </td>
          <td class="px-4 py-2 text-gray-700">
            <img src="data:image/png;base64,${field.fieldImage2}" class="w-16 h-16 rounded" alt="Field Image 2" />
          </td>
          <td class="px-4 py-2 text-center">
            <button class="text-blue-500 hover:text-blue-700 mx-2" onclick="editField(${field.fieldCode})">
              <i class="fas fa-edit"></i>
            </button>
            <button class="text-red-500 hover:text-red-700 mx-2" onclick="deleteField(${field.fieldCode})">
              <i class="fas fa-trash-alt"></i>
            </button>
          </td>
        </tr>`;
      tableBody.insertAdjacentHTML("beforeend", row);
    });
  } catch (error) {
    console.error("Error loading fields:", error);
  }
}

// Save or update field
async function saveOrUpdateField() {
  const fieldName = document.querySelector('input[id="fieldName"]').value;
  const fieldLocation = document.querySelector('input[id="fieldLocation"]').value;
  const extentSize = document.querySelector('input[id="extentSize"]').value;
  const fieldImage1File = document.querySelector("#image-upload-field1").files[0];
  const fieldImage2File = document.querySelector("#image-upload-field2").files[0];
  const button = document.querySelector("button[data-mode]");

  try {
    const fieldImage1 = fieldImage1File ? await getBase64(fieldImage1File) : null;
    const fieldImage2 = fieldImage2File ? await getBase64(fieldImage2File) : null;

    const method = button.dataset.mode === "edit" ? "PUT" : "POST";
    alertType = button.dataset.mode === "edit" ? "Field updated successfully!" : "Field saved successfully!"
    const fieldCode = button.dataset.fieldCode || "";
    const url = method === "POST" ? apiUrl : `${apiUrl}/update/${fieldCode}`;
    const formData = new FormData();

    formData.append("fieldName", fieldName);
    formData.append("fieldLocation", fieldLocation);
    formData.append("extentSize", extentSize);
    if (fieldImage1) formData.append("fieldImage1", fieldImage1File);
    if (fieldImage2) formData.append("fieldImage2", fieldImage2File);

    await fetch(url, { method, body: formData });
    alert(alertType);
    resetForm();
    loadFields();
  } catch (error) {
    console.error("Error saving field:", error);
  }
}

// Edit field
async function editField(fieldCode) {
  console.log(fieldCode)
  try {
    const response = await fetch(`${apiUrl}/get/${fieldCode}`);
    const field = await response.json();

    document.querySelector('input[id="fieldName"]').value = field.fieldName;
    document.querySelector('input[id="fieldLocation"]').value = field.fieldLocation;
    document.querySelector('input[id="extentSize"]').value = field.extentSize;
    document.querySelector("#image-upload-field1").files[0] = field.fieldImage1;
    document.querySelector("#image-upload-field2").files[0] = field.fieldImage2;

    // Handle image 
    const imageUpload1 = document.getElementById('image-upload-field1');
    if (field.fieldImage1) {
      // Convert base64 to File object
      const byteCharacters1 = atob(field.fieldImage1);
      const byteNumbers1 = new Array(byteCharacters1.length);
      for (let i = 0; i < byteCharacters1.length; i++) {
        byteNumbers1[i] = byteCharacters1.charCodeAt(i);
      }
      const byteArray1 = new Uint8Array(byteNumbers1);
      const blob1 = new Blob([byteArray1], { type: 'image/png' }); // Adjust mime type if needed
      
      // Create a File object
      const file1 = new File([blob1], 'field-image-1.png', { type: 'image/png' });
      
      // Create a DataTransfer object to set files
      const dataTransfer1 = new DataTransfer();
      dataTransfer1.items.add(file1);
      imageUpload1.files = dataTransfer1.files;

      // Optional: Show image preview
      const preview1 = document.getElementById('image-preview-field1');
      if (preview1) {
        preview1.src = `data:image/png;base64,${field.fieldImage1}`;
      }
    }

    const imageUpload2 = document.getElementById('image-upload-field2');
    if (field.fieldImage2) {
      // Convert base64 to File object
      const byteCharacters2 = atob(field.fieldImage2);
      const byteNumbers2 = new Array(byteCharacters2.length);
      for (let i = 0; i < byteCharacters2.length; i++) {
        byteNumbers2[i] = byteCharacters2.charCodeAt(i);
      }
      const byteArray2 = new Uint8Array(byteNumbers2);
      const blob2 = new Blob([byteArray2], { type: 'image/png' }); // Adjust mime type if needed
      
      // Create a File object
      const file2 = new File([blob2], 'field-image-2.png', { type: 'image/png' });
      
      // Create a DataTransfer object to set files
      const dataTransfer2 = new DataTransfer();
      dataTransfer2.items.add(file2);
      imageUpload2.files = dataTransfer2.files;

      // Optional: Show image preview
      const preview2 = document.getElementById('image-preview-field2');
      if (preview2) {
        preview2.src = `data:image/png;base64,${field.fieldImage2}`;
      }
    }

    const button = document.querySelector("button[data-mode]");
    button.textContent = "Update Field";
    button.dataset.mode = "edit";
    button.dataset.fieldCode = fieldCode;
  } catch (error) {
    console.error("Error editing field:", error);
  }
}

// Delete field
async function deleteField(fieldCode) {
  if (!confirm("Are you sure to delete this data?")) return;

  try {
    await fetch(`${apiUrl}/delete/${fieldCode}`, { method: "DELETE" });
    alert("Field deleted successfully!");
    loadFields();
  } catch (error) {
    console.error("Error deleting field:", error);
  }
}

// Reset form to default state
function resetForm() {
  document.querySelector('input[placeholder="Rice Palate A"]').value = "";
  document.querySelector('input[placeholder="Colombo, Sri Lanka"]').value = "";
  document.querySelector('input[placeholder="2000 Sq.mt"]').value = "";
  document.querySelector("#image-upload-field1").value = "";
  document.querySelector("#image-upload-field2").value = "";

  const button = document.querySelector("button");
  button.textContent = "Add Field";
  button.dataset.mode = "add";
  button.removeAttribute("data-field-code");
}

// Load fields on page load
document.addEventListener("DOMContentLoaded", () => {
  // Only run these if the elements exist
  const tableBody = document.querySelector("tbody");
  if (tableBody) {
    loadFields();
  }

  const saveButton = document.querySelector("button[data-mode]");
  if (saveButton) {
    saveButton.addEventListener("click", saveOrUpdateField);
  } else {
    console.log("Save button not found. This might be okay if not on the field page.");
  }
});



// Attach preview functions to the window object to make them globally accessible
window.previewImageField1 = function(event) {
  const file = event.target.files[0]; // Get the selected file
  if (file) {
    const reader = new FileReader();
    reader.onload = function (e) {
      // Optional: If you want to show a preview, uncomment and add an img element with id='image-preview-field1'
      // const preview = document.getElementById('image-preview-field1');
      // preview.src = e.target.result;
      console.log("Field 1 image selected:", file.name);
    };
    reader.readAsDataURL(file); // Read the file as a Data URL
  }
}

window.previewImageField2 = function(event) {
  const file = event.target.files[0]; // Get the selected file
  if (file) {
    const reader = new FileReader();
    reader.onload = function (e) {
      // Optional: If you want to show a preview, uncomment and add an img element with id='image-preview-field2'
      // const preview = document.getElementById('image-preview-field2');
      // preview.src = e.target.result;
      console.log("Field 2 image selected:", file.name);
    };
    reader.readAsDataURL(file); // Read the file as a Data URL
  }
}