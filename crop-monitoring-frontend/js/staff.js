const apiUrlStaff = "http://localhost:8080/staff";

// const API_BASE_URL_FIELD = "http://localhost:8080/field"

let alertTypeStaff = "Staff add successfully!";


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

function loadDesignationAndRole() {
  // Load designations
  fetch(`${apiUrlStaff}/designations`)
    .then(response => response.json())
    .then(designations => {
      const desigDropdown = document.getElementById("designation");
      if (!desigDropdown) {
        console.error("Designation dropdown not found");
        return;
      }
      console.log("Designation:", designations);

      // Check if categories is an array of strings or objects
      desigDropdown.innerHTML = designations.map(des =>
        typeof des === "string" 
          ? `<option value="${des}">${des}</option>` 
          : `<option value="${des.id || des.name}">${des.name}</option>`
      ).join('');
    })
    .catch(error => console.error("Error loading designations:", error));

    // Load designations
  fetch(`${apiUrlStaff}/roles`)
    .then(response => response.json())
    .then(roles => {
      const rolesDropdown = document.getElementById("role");
      if (!rolesDropdown) {
        console.error("Role dropdown not found");
        return;
      }
      console.log("Roles:", roles);

      // Check if categories is an array of strings or objects
      rolesDropdown.innerHTML = roles.map(r =>
        typeof r === "string" 
          ? `<option value="${r}">${r}</option>` 
          : `<option value="${r.id || r.name}">${r.name}</option>`
      ).join('');
    })
    .catch(error => console.error("Error loading roles:", error));
}

// Function to load all fields and populate the table
async function loadStaffs() {
  try {
    const response = await fetch(`${apiUrlStaff}/all_staff`);
    const staffs = await response.json();

    const tableBody = document.querySelector("tbody");
    if (!tableBody) {
      console.log("Table body not found. Skipping Staff population.");
      return;
    }
    
    tableBody.innerHTML = ""; // Clear existing rows

    staffs.forEach((staff) => {
      const row = `
        <tr class="border-t border-gray-200 hover:bg-gray-50">
          <td class="px-4 py-2 text-gray-700">${staff.staffId}</td>
          <td class="px-4 py-2 text-gray-700">${staff.role}</td>
          <td class="px-4 py-2 text-gray-700">${staff.firstName}</td>
          <td class="px-4 py-2 text-gray-700">${staff.joinedDate}</td>
          <td class="px-4 py-2 text-gray-700">${staff.email}</td>
          <td class="px-4 py-2 text-gray-700">${staff.contactNo}</td>
          <td class="px-4 py-2 text-gray-700">${staff.mainCity}</td>
          <td class="px-4 py-2 text-gray-700">${staff.designation}</td>
          <td class="px-4 py-2 text-center">
            <button class="text-blue-500 hover:text-blue-700 mx-2" onclick="editStaff(${staff.staffId})">
              <i class="fas fa-edit"></i>
            </button>
            <button class="text-red-500 hover:text-red-700 mx-2" onclick="deleteStaff(${staff.staffId})">
              <i class="fas fa-trash-alt"></i>
            </button>
          </td>
        </tr>`;
      tableBody.insertAdjacentHTML("beforeend", row);
    });
  } catch (error) {
    console.error("Error loading staffs:", error);
  }
}

// Save or update staff
async function saveOrUpdateStaff() {
  const firstName = document.querySelector('input[id="firstName"]').value;
  const lastName = document.querySelector('input[id="lastName"]').value;
  const designation = document.getElementById('designation').value;
  const gender = document.querySelector('input[name="gender"]:checked').value;
  const joinedDate = document.getElementById('joinedDate').value;
  const dOB = document.getElementById('dOB').value;
  const contactNo = document.getElementById('contactNo').value;
  const buildingNo = document.getElementById('buildingNo').value;
  const lane = document.getElementById('lane').value;
  const mainCity = document.getElementById('mainCity').value;
  const mainState = document.getElementById('mainState').value;
  const postalCode = document.getElementById('postalCode').value;
  const email = document.getElementById('email').value;
  const role = document.getElementById('role').value;
  const button = document.getElementById("btnStaff");

  try {
    
    const method = button.dataset.mode === "edit" ? "PUT" : "POST";
    alertTypeStaff = button.dataset.mode === "edit" ? "Staff updated successfully!" : "Staff add successfully!"
    const staffId = button.dataset.staffId || "";
    const url = method === "POST" ? apiUrlStaff : `${apiUrlStaff}/update/${staffId}`;
    // Create a JSON object
    const staffData = {
      firstName,
      lastName,
      designation,
      gender,
      joinedDate,
      dOB,
      contactNo,
      buildingNo,
      lane,
      mainCity,
      mainState,
      postalCode,
      email,
      role,
    };

    // Send JSON data
    await fetch(url, {
      method,
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(staffData),
    });

    alert(alertTypeStaff);
    resetFormStaff();
    loadStaffs();
  } catch (error) {
    console.error("Error saving staff:", error);
  }
}

// Edit field
async function editStaff(staffId) {
  console.log(staffId)
  try {
    const response = await fetch(`${apiUrlStaff}/get/${staffId}`);
    const staff = await response.json();

    document.querySelector('input[id="firstName"]').value = staff.firstName
    document.querySelector('input[id="lastName"]').value = staff.lastName
    // document.querySelector('input[id=designation]').value = staff.designation
    // document.querySelector('input[name="gender"]:checked').value = staff.gender
    document.querySelector('input[id=joinedDate]').value = staff.joinedDate
    document.querySelector('input[id=dOB]').value = staff.dOB
    document.querySelector('input[id=contactNo]').value = staff.contactNo
    document.querySelector('input[id=buildingNo]').value = staff.buildingNo
    document.querySelector('input[id=lane]').value = staff.lane
    document.querySelector('input[id=mainCity]').value = staff.mainCity
    document.querySelector('input[id=mainState]').value = staff.mainState
    document.querySelector('input[id=postalCode]').value = staff.postalCode
    document.querySelector('input[id=email]').value = staff.email
    // document.querySelector('input[id=role]').value = staff.role

    // Set select fields
    const desigDropdown = document.getElementById('designation');
    if (desigDropdown) {
      desigDropdown.value = staff.designation;
    }

    const rolesDropdown = document.getElementById('role');
    if (rolesDropdown) {
      rolesDropdown.value = staff.role;
    }

     // Set radio button for crop season
     const genderRadios = document.querySelectorAll('input[name="gender"]');
     genderRadios.forEach(radio => {
       if (radio.value === staff.gender) {
         radio.checked = true;
       }
     });

    const button = document.getElementById("btnStaff");
    button.textContent = "Update Staff";
    button.dataset.mode = "edit";
    button.dataset.staffId = staffId;
  } catch (error) {
    console.error("Error editing Staff:", error);
  }
}

// Delete field
async function deleteStaff(staffId) {
  if (!confirm("Are you sure to delete this data?")) return;

  try {
    await fetch(`${apiUrlStaff}/delete/${staffId}`, { method: "DELETE" });
    alert("Staff deleted successfully!");
    loadStaffs();
  } catch (error) {
    console.error("Error deleting staff:", error);
  }
}

// Reset form to default state
function resetFormStaff() {
  document.querySelector('input[id="firstName"]').value = "";
    document.querySelector('input[id="lastName"]').value = "";
    document.getElementById('designation').value = "";
    document.querySelector('input[name="gender"]:checked').value = "";
    document.getElementById('joinedDate').value = "";
    document.getElementById('dOB').value = "";
    document.getElementById('contactNo').value = "";
    document.getElementById('buildingNo').value = "";
    document.getElementById('lane').value = "";
    document.getElementById('mainCity').value = "";
    document.getElementById('mainState').value = "";
    document.getElementById('postalCode').value = "";
    document.getElementById('email').value = "";
    document.getElementById('role').value = "";
  
  const button = document.querySelector("button");
  button.textContent = "Add Staff";
  button.dataset.mode = "add";
  button.removeAttribute("data-staff-code");
}

// Load fields on page load
document.addEventListener("DOMContentLoaded", () => {

  loadDesignationAndRole();
  // Only run these if the elements exist
  const tableBody = document.querySelector("tbody");
  if (tableBody) {
    loadStaffs();
  }

  const saveButtonStaff = document.getElementById("btnStaff");
  if (saveButtonStaff) {
    saveButtonStaff.addEventListener("click", saveOrUpdateStaff);
  } else {
    console.log("Save button not found. This might be okay if not on the Staff page.");
  }
});

function filterStaffTable() {
  const searchValue = document.getElementById("searchStaff").value.toLowerCase();
  const table = document.getElementById("staffTable");
  const rows = table.querySelectorAll("tbody tr");

  rows.forEach((row) => {
    const cells = row.querySelectorAll("td");
    const role = cells[1]?.textContent.toLowerCase() || "";
    const firstName = cells[2]?.textContent.toLowerCase() || "";
    const joinedDate = cells[3]?.textContent.toLowerCase() || "";
    const email = cells[4]?.textContent.toLowerCase() || "";
    const contactNo = cells[5]?.textContent.toLowerCase() || "";
    const mainCity = cells[6]?.textContent.toLowerCase() || "";
    const designation = cells[7]?.textContent.toLowerCase() || "";

    // Match search value with any of the fields
    if (
      role.includes(searchValue) ||
      firstName.includes(searchValue) ||
      joinedDate.includes(searchValue) ||
      email.includes(searchValue) ||
      contactNo.includes(searchValue) ||
      mainCity.includes(searchValue) ||
      designation.includes(searchValue)
    ) {
      row.style.display = ""; // Show row
    } else {
      row.style.display = "none"; // Hide row
    }
  });
}
