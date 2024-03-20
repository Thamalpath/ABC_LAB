getAllProduct()
function getAllProduct() {
    const userName = sessionStorage.getItem("userName");
    $.ajax({
        method: "POST",
        url: "http://localhost:8080/api/v1/appoinment/consultant-name/"+userName,
        async: true,
        success: function (data) {
            populateTable(data);
        },
        error: function (xhr, exception) {
            alert("Error");
        },
    });
}

function populateTable(data) {
    var tableBody = $("#prodTable");
    // Clear the existing table body
    tableBody.empty();
    // Loop through the data and append rows to the table
    $.each(data, function (index, element) {
        var row = $("<tr>");
        row.append($("<td>").text(element.id));
        row.append($("<td>").text(element.c_name));
        row.append($("<td>").text(element.u_name));
        row.append($("<td>").text(element.book_date));
        row.append($("<td>").text(element.time));
        row.append($("<td>").text(element.contact));
        row.append($("<td>").text(element.mail));
        row.append($("<td>").text(element.status));


// Create buttons and add them to the row
var editButton = $("<button>").text("Approve").addClass("btn btn-primary btn-sm mr-2");
var deleteButton = $("<button>").text("Decline").addClass("btn btn-danger btn-sm");





// Add click event listeners to the buttons
  // Add click event listener for "Edit" button
     // Add click event listener for "Edit" button
    editButton.click(function () {
        var appointmentId = element.id; // Assuming element.id contains the appointment ID
        $.ajax({
            url: "http://localhost:8080/api/v1/appoinment/appointments/" + appointmentId + "/approve",
            type: "POST",
            success: function(response) {
                // Handle success
            editButton.prop('disabled', true); // Disable the approve button
            deleteButton.prop('disabled', true); // Disable the reject button
                console.log("Appointment approved successfully:", response);
                location.reload();
            },
            error: function(xhr, status, error) {
                // Handle error, e.g., show error message
                console.error("Error approving appointment:", error);
            }
        });
    });
    



deleteButton.click(function () {

    var appointmentId = element.id; // Assuming element.id contains the appointment ID
    $.ajax({
        url: "http://localhost:8080/api/v1/appoinment/appointments/" + appointmentId + "/reject",
        type: "POST",
        success: function(response) {
            // Handle success, e.g., hide buttons, update UI, etc.
             // Handle success
            editButton.prop('disabled', true); // Disable the approve button
            deleteButton.prop('disabled', true); // Disable the reject button
            console.log("Appointment approved successfully:", response);
            location.reload();
        },
        error: function(xhr, status, error) {
            // Handle error, e.g., show error message
            console.error("Error approving appointment:", error);
        }
    });
    
});
// Append the buttons to the row
row.append($("<td>").append(editButton).append(deleteButton));
        tableBody.append(row);
    });
}
function showUserName() {
    const userName = sessionStorage.getItem("userName");
    if (userName) {
        console.log(`User's name: ${userName}`);
    } else {
        console.log("User's name not found in sessionStorage.");
    }
}
window.onload = showUserName;

