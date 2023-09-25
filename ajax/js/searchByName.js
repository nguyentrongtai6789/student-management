
function searchByName() {
    document.getElementById("form-create-student").style.display = "none";
    document.getElementById("listStudent").style.display = "none";
    document.getElementById("btn-home").style.display = "block";
    document.getElementById("pagination").style.display = "none";
    document.getElementById("homie").style.display = "block";
    document.getElementById("btn-create-student").style.display = "block";
    document.getElementById("form-edit-student").style.display = "block";
    document.getElementById("listStudent").style.display = "none";
    document.getElementById("subject-checked").innerHTML="";
    document.getElementById("search_by_name").style.display = "block";

    let name = $('#name').val();
    $.ajax({
        headers:{
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "POST",
        data:"",
        url: "http://localhost:8080/api/students/searchByname" + name + "&size=3",
        success: function (response){
            displayData(response.content);
            displayPagination(response.number, response.totalPages);
        }
    })
}
function displayData(students) {
    let content = '    <table id="list-student-home"><tr>\n' +
        '        <th width="200px">Họ và tên</td>\n' +
        '        <th width="150px">Địa chỉ</td>\n' +
        '        <th width="100px">Ảnh</td>\n' +
        '        <th width="180px">Các môn học đã đăng kí</td>\n' +
        '        <th width="120px">Trạng thái</td>\n' +
        '        <th width="80px">Edit</td>\n' +
        '        <th width="80px">Delete</td>\n' +
        '        <th width="80px">View</td>\n' +
        '    </tr>';
    for (let i = 0; i < students.length; i++) {
        content += getStudent(students[i]);
    }
    content += "</table>"
    document.getElementById("listStudent").innerHTML = content;
    for (let i = 0; i < students.length; i++) {
        getListSubjectOfStudent(students[i])
    }
}
function displayPagination(currentPage, totalPages) {
    var pagination = $('#pagination');
    pagination.empty();
    if (totalPages > 1) {
        var prevButton = '<button id="prevButton" onclick="loadData(' + (currentPage - 1) + ')">Previous</button>';
        pagination.append(prevButton);
    }

    for (var i = 0; i < totalPages; i++) {
        var pageNumber = i;
        if (i === currentPage) {
            pagination.append('<span class="currentPage">' + pageNumber + '</span>');
        } else {
            var pageButton = '<button onclick="loadData(' + pageNumber + ')">' + pageNumber + '</button>';
            pagination.append(pageButton);
        }
    }

    if (totalPages > 1) {
        var nextButton = '<button id="nextButton" onclick="loadData(' + (currentPage + 1) + ')">Next</button>';
        pagination.append(nextButton);
    }
}
