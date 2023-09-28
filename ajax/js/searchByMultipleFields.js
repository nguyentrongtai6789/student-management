function listAddress() {
    let i;
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/subject",
        success: function (nameSubjects) {
            let content = "";
            for (let j = 0; j < nameSubjects.length; j++) {
                content = "<label for=\"subject" + i + "\">" + nameSubjects[i] + "</label>" +
                    "<input type=\"checkbox\" id=\"subject" + i + "\" value=\"" + nameSubjects[i] + "\">";
            }
            content += "<br>";
            document.getElementById("subject").innerHTML = content;
        }
    })
}

function listStatus() {

    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/status",
        success: function (nameStatus) {
            let content = "";
            for (let j = 0; j < nameStatus.length; j++) {
                content = "<label for=\"subject" + i + "\">" + nameStatus[i] + "</label>" +
                    "<input type=\"checkbox\" id=\"subject" + i + "\" value=\"" + nameStatus[i] + "\">";
            }
            content += "<br>";
            document.getElementById("status").innerHTML = content;
        }
    })
}

function searchByMultipleFields() {
    let nameCity = $('#city').val();
    let status = $('#demo').val();
    let title = {
        address: nameCity,
        status: status
    }
    console.log(title)

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "POST",
        data: JSON.stringify(title),
        url: "http://localhost:8080/api/title",
        success: function (data) {
            displayDataS(data)
        }
    })
}

function displayDataS(students) {
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
        content += getStudentS(students[i]);
    }
    content += "</table>"
    document.getElementById("listStudent").innerHTML = content;
    for (let i = 0; i < students.length; i++) {
        getListSubjectOfStudentS(students[i])
    }
}

function getStudentS(student) {
    return `<tr><td >${student.name}</td><td >${student.address}</td><td >
           <img style="width: 100px; height: 100px" src="\\src\\main\\webapp\\image\\${student.url_img}" alt="Khong co anh"></td>` +
        `<td id="subject-checked-${student.id}">` + `</td><td>${student.status.name}</td>` +
        `<td><button onclick="deleteStudent(${student.id})">Delete</button></td>` +
        `<td><button onclick="showFormEditStudent(${student.id})">Edit</button></td>` +
        `<td><button onclick="">View</button></td></tr>`;
}

function getListSubjectOfStudentS(student) {
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

// function showListByTitle() {
//     $.ajax({
//         type: "GET",
//         url: "http://localhost:8080/api/title",
//         success: function (students) {
//             console.log("line 24")
//             content = getTableTitle2();
//             if (students !== null && students.length !== 0) {
//                 for (let i = 0; i < students.length; i++) {
//                     content += getStudent2(students[i]);
//                 }
//             }
//             content += "</table>";
//             document.getElementById("list-st").innerHTML = content;
//         }
//     })
// }
//
// function getStudent2(student) {
//     listDetail2(student);
//     return "<tr>" +
//         "<td>" + student.name + "</td>" +
//         "<td>" + student.address + "</td>" + //C:\Users\admin\Desktop\student-management\src\main\webapp\image\_55497003_h4020548-charles_babbage,_english_mathematician-spl.jpg
//         "<td><img class=\"img_in_table\" src=\"..\\..\\src\\main\\webapp\\image\\" + student.url_img + "\"></td>" +
//         "<td><div id=\"listDetail\"></div></td>" +
//         "<td>" + student.status.name + "</td>" +
//         "<td><button onclick=\"deleteStudent(" + student.id + ")\">Delete</button></td>" +
//         "<td><button onclick=\" showFormEditStudent(" + student.id + ")\">Edit</button></td>" +
//         "<td><button onclick=\"\">View</button></td>" +
//         "</tr>";
// }

// function getTableTitle2() {
//     return ' <table id="list-student-home"><tr>\n' +
//         '        <th width="200px">Họ và tên</td>\n' +
//         '        <th width="150px">Địa chỉ</td>\n' +
//         '        <th width="100px">Ảnh</td>\n' +
//         '        <th width="180px">Các môn học đã đăng kí</td>\n' +
//         '        <th width="120px">Trạng thái</td>\n' +
//         '        <th width="80px">Edit</td>\n' +
//         '        <th width="80px">Delete</td>\n' +
//         '        <th width="80px">View</td>\n' +
//         '    </tr>';
// }

// function listDetail2(student) {
//     $.ajax({
//         type: "GET",
//         url: "http://localhost:8080/api/subject/detail_student_and_subject/" + student.id,
//         success: function (details) {
//             let content1 = "";
//             if (details !== null && details.length !== 0) {
//                 for (let i = 0; i < details.length; i++) {
//                     content1 += details[i].name;
//                 }
//             }
//             document.getElementById("listDetail").innerHTML = content1;
//         }
//     })
// }

function showFormSearch() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/status",
        success: function (data) {
            let content = "<select id='demo'>";
            if (data !== null && data.length !== 0) {
                for (let i = 0; i < data.length; i++) {
                    content +=  "<option value= " + data[i].id + ">" + data[i].name + "</option>";
                    console.log("data =" + data);
                }
                content += "</select>";
            }
            document.getElementById("status").innerHTML = content;
        }
    })

}