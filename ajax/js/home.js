function loadData(page) {
    document.getElementById("form-create-student").style.display = "none";
    document.getElementById("listStudent").style.display = "block";
    document.getElementById("btn-home").style.display = "none";
    document.getElementById("pagination").style.display = "block";
    document.getElementById("homie").style.display = "block";
    document.getElementById("btn-create-student").style.display = "block";
    document.getElementById("form-edit-student").style.display = "none";
    document.getElementById("list-subject").style.display = "none";
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/students?page=' + page + '&size=3', // Thay đổi kích thước trang tùy ý
        success: function (response) {
            displayData(response.content); // truyền vào một list student, page.content = list
            displayPagination(response.number, response.totalPages);

        }
    });
    console.log(page)
}

// hiện danh sách student:
function displayData(students) {
    // var studentList = $('#listStudent');
    // studentList.empty();
    //
    // for (var i = 0; i < students.length; i++) {
    //     var student = students[i];
    //     studentList.append('<div>' + student.name + ' - ' + student.address + '</div>');
    // }
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

function getAllSubjectToSelect() {
    document.getElementById("subject-to-select").innerHTML = "";
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/students//getAllSubject',
        success: function (listSubject) {
            let container = document.getElementById('subject-to-select');
            listSubject.forEach(function (subject) {
                let checkbox = document.createElement('input');
                checkbox.type = 'checkbox';
                checkbox.name = 'subject';
                checkbox.value = subject.id;
                let label = document.createElement('label');
                label.appendChild(document.createTextNode(subject.name));
                container.appendChild(checkbox);
                container.appendChild(label);
                container.appendChild(document.createElement('br'));
            })
        }
    });
}

function getListSubjectOfStudent(student) {
    let id = student.id;
    let content = "";
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/students/getSubjectCheckedByStudent/' + id,
        success: function (listSubject) {
            for (let i = 0; i < listSubject.length; i++) {
                content += listSubject[i].name + '<br>'
            }
            document.getElementById('subject-checked').innerHTML = content;
            document.getElementById(`subject-checked-${student.id}`).innerHTML = content;
        }
    });
}

function getStudent(student) {
    return `<tr><td >${student.name}</td><td >${student.address}</td><td >
           <img style="width: 100px; height: 100px" src="\\src\\main\\webapp\\image\\${student.url_img}" alt="Khong co anh"></td>` +
        `<td id="subject-checked-${student.id}">` + `</td><td>${student.status.name}</td>` +
        `<td><button onclick="deleteStudent(${student.id})">Delete</button></td>` +
        `<td><button onclick="showFormEditStudent(${student.id})">Edit</button></td>` +
        `<td><button onclick="">View</button></td></tr>`;
}

let idEdit;

function showFormEditStudent(id) {
    console.log(id)
    document.getElementById("form-create-student").style.display = "none";
    document.getElementById("listStudent").style.display = "none";
    document.getElementById("btn-home").style.display = "block";
    document.getElementById("pagination").style.display = "none";
    document.getElementById("homie").style.display = "block";
    document.getElementById("btn-create-student").style.display = "block";
    document.getElementById("form-edit-student").style.display = "block";
    document.getElementById("form-edit-student").reset();
    document.getElementById("image-edit").innerHTML = "";
    document.getElementById("listStudent").style.display = "none";
    document.getElementById("subject-checked").innerHTML = "";
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "GET",
        //tên API
        url: `http://localhost:8080/api/students/getStudentById/${id}`,
        //xử lý khi thành công
        success: function (student) {
            // hiện thông tin lên form edit
            idEdit = student.id
            console.log(student.id)
            $('#name-edit').val(student.name);
            console.log(student.name)
            $('#address-edit').val(student.address);
            $('#id_status-edit').val(student.status.id);
            let img = document.getElementById("image-edit");
            img.src = "\\src\\main\\webapp\\image\\" + student.url_img;
            getListSubjectOfStudent(student)
        }
    });
    getAllSubjectToSelect();
}


function saveEditStudent() {
    let name = $('#name-edit').val();
    let address = $('#address-edit').val();
    let multipartFile = $('#file-image-edit')[0].files[0];
    let id_status = $('#id_status-edit').val();
    let subjectSelected = [];
    $("input[name='subject']:checked").each(function () {
        subjectSelected.push($(this).val());
    });
    let formData = new FormData();
    formData.append("name", name);
    formData.append("address", address);
    formData.append("id_status", id_status);
    formData.append("id", idEdit);
    formData.append("arrayIdSubject", subjectSelected);
    if ($('#file-image-edit')[0].files.length > 0) {
        formData.append("multipartFile", multipartFile);
        // trường hợp chọn ảnh thì gửi multipartfile lên:
        $.ajax({
            url: "http://localhost:8080/api/students",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            success: function (data) {
                loadData(0);
                alert(data);
            },
            error: function (xhr, status, error) {
                let errorMessage = JSON.parse(xhr.responseText);
                displayErrors(errorMessage);
            }
        });
        event.preventDefault();
    } else {
        // trường hợp không chọn ảnh: không đưa multipartfile lên:
        $.ajax({
            url: "http://localhost:8080/api/students",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            success: function (data) {
                loadData(0);
                alert(data);
            },
            error: function (xhr, status, error) {
                let errorMessage = JSON.parse(xhr.responseText);
                displayErrors(errorMessage);
            }
        });
        event.preventDefault();
    }
}

function deleteStudent(id) {
    console.log(id)
    $.ajax({
        type: "DELETE",
        //tên API
        url: `http://localhost:8080/api/students/delete/${id}`,
        //xử lý khi thành công
        success: function (data) {
            loadData(0)
            alert(data)
            console.log(data)
        }
    });
}

// hiện nút bấm
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

function showFormCreate() {
    document.getElementById("form-create-student").reset();
    document.getElementById("valid-name").innerHTML = "";
    document.getElementById("valid-address").innerHTML = "";
    document.getElementById("form-create-student").style.display = "block";
    document.getElementById("listStudent").style.display = "none";
    document.getElementById("homie").style.display = "none";
    document.getElementById("btn-create-student").style.display = "none";
    document.getElementById("pagination").style.display = "none";
    document.getElementById("btn-home").style.display = "block";
}



function addNewStudent() {
    let name = $('#name').val();
    let address = $('#address').val();
    let multipartFile = $('#file-image')[0].files[0];
    let id_status = $('#id_status').val();
    let formData = new FormData();
    formData.append("name", name);
    formData.append("address", address);
    if ($('#file-image')[0].files.length > 0) {
        formData.append("multipartFile", multipartFile);
    } else {
        let defaultImageFile = new File(["default"], "src/main/webapp/image/default.jpg", { type: "image/jpeg/jpg" });
        formData.append("multipartFile", defaultImageFile);
    }
    formData.append("id_status", id_status);
    formData.append("id", 0);
    $.ajax({
        url: "http://localhost:8080/api/students/addNewStudent",
        type: "POST",
        data: formData,
        processData: false,
        contentType: false,
        success: function (data) {
            loadData(0);
            alert(data);
        },
        error: function (xhr, status, error) {
            let errorMessage = JSON.parse(xhr.responseText);
            displayErrors(errorMessage);
        }
    });
    // console.log("a");
    // console.log(formData);
    event.preventDefault();
}

function displayErrors(error) {
    if (error.name) {
        $('#valid-name').text(error.name);
    } else {
        $('#valid-name').text('');
    }
    if (error.address) {
        $('#valid-address').text(error.address);
    } else {
        $('#valid-address').text('');
    }
}

function showListSubject() {
    document.getElementById("form-create-student").style.display = "none";
    document.getElementById("listStudent").style.display = "none";
    document.getElementById("btn-home").style.display = "block";
    document.getElementById("pagination").style.display = "none";
    document.getElementById("homie").style.display = "none";
    document.getElementById("btn-create-student").style.display = "none";
    document.getElementById("form-edit-student").style.display = "none";
    document.getElementById("list-subject").style.display = "block";
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/students/getListSubject',
        success: function (list) {
            displayListSubject(list)
            console.log(list)
        }
    });
}

function displayListSubject(list) {
    let content = '    <table id="table-list-subject"><tr>' +
        '        <th width="200px">Số thứ tự</td>' +
        '        <th width="150px">Tên môn học</td>' +
        '        <th width="200px">Số học sinh đã đăng ký</td>' +
        '        <th width="200px">Delete</th></td>' +
        '        <th width="200px">Edit</td>' +
        '        <th width="200px">View</td>' +
        '    </tr>';
    for (let i = 0; i < list.length; i++) {
        content += `<tr><td >${i + 1}</td>` +
            `<td>${list[i].name}</td>` +
            `<td>${list[i].count_student}</td>` +
            `<td><button onclick="">Delete</button></td>` +
            `<td><button onclick="">Edit</button></td>` +
            `<td><button onclick="">View</button></td></tr>`;
    }
    content += "</table>"
    document.getElementById("list-subject").innerHTML = content;
}

