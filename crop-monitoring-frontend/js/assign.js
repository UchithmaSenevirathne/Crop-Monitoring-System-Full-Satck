const apiUrlAssign = "http://localhost:8080/field_staff";

// const API_BASE_URL_FIELD = "http://localhost:8080/field"

function loadDropDownAssign() {
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

    // Load staff
  fetch(`${apiUrlStaff}/all_staff`)
  .then((response) => response.json())
  .then((staffs) => {
    const staffDropdown = document.getElementById("staffs");
    if (!staffDropdown) {
      console.error("Staff dropdown not found");
      return;
    }
    console.log("Staffs:", staffs);

    // Check if fields have a `name` property
    staffDropdown.innerHTML = staffs
      .map((st) => `<option value="${st.staffId}">${st.firstName}</option>`)
      .join("");
  })
  .catch((error) => console.error("Error loading staffs:", error));
}

// Function to load all fields and populate the table
async function loadAssign() {
  try {
    const response = await fetch(`${apiUrlAssign}/all_assigns`);
    const assigns = await response.json();

    const tableBody = document.querySelector("tbody");
    if (!tableBody) {
      console.log("Table body not found. Skipping Assign population.");
      return;
    }
    
    tableBody.innerHTML = ""; // Clear existing rows

    assigns.forEach((as) => {
      const row = `
        <tr class="border-t border-gray-200 hover:bg-gray-50">
          <td class="px-4 py-2 text-gray-700">${as.field_staff_id}</td>
          <td class="px-4 py-2 text-gray-700">${as.assignedDate}</td>
          <td class="px-4 py-2 text-gray-700">${as.dueDate}</td>
          <td class="px-4 py-2 text-gray-700">${as.fieldCode}</td>
          <td class="px-4 py-2 text-gray-700">${as.staffId}</td>
          <td class="px-4 py-2 text-center">
            <button class="text-red-500 hover:text-red-700 mx-2" onclick="deleteAssign(${as.field_staff_id})">
              <i class="fas fa-trash-alt"></i>
            </button>
          </td>
        </tr>`;
      tableBody.insertAdjacentHTML("beforeend", row);
    });
  } catch (error) {
    console.error("Error loading assigns:", error);
  }
}

// Save or update field
async function assignStaffToField() {
  const assignedDate = document.getElementById('assignedDate').value;
  const dueDate = document.getElementById('dueDate').value;
  const fieldCode = document.getElementById('fields').value;
  const staffId = document.getElementById('staffs').value;

  try {
   console.log(fieldCode);
   console.log(staffId);

    const method = "POST";
    const url = apiUrlAssign;

    const assignData = {
        assignedDate,
        dueDate,
        fieldCode,
        staffId
      };
  
      // Send JSON data
      await fetch(url, {
        method,
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(assignData),
      });

    alert("Staff assign to field successfully");
    resetFormAssign();
    loadAssign();
  } catch (error) {
    console.error("Error saving assign:", error);
  }
}

// Delete
async function deleteAssign(field_staff_id) {
  if (!confirm("Are you sure to delete this data?")) return;

  try {
    await fetch(`${apiUrlAssign}/delete/${field_staff_id}`, { method: "DELETE" });
    alert("Assign cancled successfully!");
    loadAssign();
  } catch (error) {
    console.error("Error deleting assign:", error);
  }
}

// Reset form to default state
function resetFormAssign() {
    document.getElementById('assignedDate').value = "";
    document.getElementById('dueDate').value = "";

  const button = document.querySelector("button");
  button.textContent = "Assign";
  button.dataset.mode = "add";
  button.removeAttribute("data-assign-code");
}

// Load fields on page load
document.addEventListener("DOMContentLoaded", () => {

  loadDropDownAssign();
  // Only run these if the elements exist
  const tableBody = document.querySelector("tbody");
  if (tableBody) {
    loadAssign();
  }

  const assignbtn = document.getElementById("btnAssign");
  if (assignbtn) {
    assignbtn.addEventListener("click", assignStaffToField);
  } else {
    console.log("Save button not found. This might be okay if not on the assign page.");
  }
});
