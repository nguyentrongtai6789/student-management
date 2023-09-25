function searchByName() {
    document.getElementById("form-create-student").style.display = "none";
    document.getElementById("listStudent").style.display = "none";
    document.getElementById("btn-home").style.display = "block";
    document.getElementById("pagination").style.display = "none";
    document.getElementById("homie").style.display = "block";
    document.getElementById("btn-create-student").style.display = "block";
    document.getElementById("form-edit-student").style.display = "none";
    document.getElementById("listStudent").style.display = "block";
    document.getElementById("subject-checked").innerHTML = "";
    document.getElementById("search_by_name").style.display = "block";

    let name = document.getElementById("searchbyname").value;
    console.log(name);
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "POST",
        data: "",
        url: "http://localhost:8080/api/students/searchByname/" + name,
        success: function (students) {
            console.log("line 24")
            content = getTableTitle();
            if (students !== null && students.length !== 0) {
                for (let i = 0; i < students.length; i++) {
                    console.log("line 38")
                    console.log(i)
                    console.log(students[i])
                    console.log(students[i].name)
                    content += getStudent1(students[i]);
                }
            }
            content += "</table>";
            document.getElementById("listStudent").innerHTML = content;
            console.log("line47")
        }
    })
}
function getTableTitle() {
    return ' <table id="list-student-home"><tr>\n' +
    '        <th width="200px">Họ và tên</td>\n' +
    '        <th width="150px">Địa chỉ</td>\n' +
    '        <th width="100px">Ảnh</td>\n' +
    '        <th width="180px">Các môn học đã đăng kí</td>\n' +
    '        <th width="120px">Trạng thái</td>\n' +
    '        <th width="80px">Edit</td>\n' +
    '        <th width="80px">Delete</td>\n' +
    '        <th width="80px">View</td>\n' +
    '    </tr>';
}

function getStudent1(student) {
    listDetail1(student);
    return "<tr>" +
        "<td>" + student.name + "</td>" +
        "<td>" + student.address + "</td>" + //C:\Users\admin\Desktop\student-management\src\main\webapp\image\_55497003_h4020548-charles_babbage,_english_mathematician-spl.jpg
        "<td><img class=\"img_in_table\" src=\"..\\..\\src\\main\\webapp\\image\\" + student.url_img + "\"></td>" +
        "<td><div id=\"listDetail\"></div></td>" +
        "<td>" + student.status.name + "</td>" +
        "<td><button onclick=\"\">Delete</button></td>" +
        "<td><button onclick=\"\">Edit</button></td>" +
        "<td><button onclick=\"\">View</button></td>" +
        "</tr>";
}


function listDetail1(student) {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/subject/detail_student_and_subject/" + student.id,
        success: function (details) {
            let content1 = "";
            if (details !== null && details.length !== 0) {
                for (let i = 0; i < details.length; i++) {
                    content1 += details[i].name;
                }
            }
            document.getElementById("listDetail").innerHTML = content1;
        }
    })
}