
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
        url: "http://localhost:8080/api/students/searchByname?" + name + "?size=3",
        success: function (students){
            content = "<table>" + "<tr>" +
                `<th>Họ và tên</th>`+
                `<th>Địa chỉ</th>`+
                `<th>Ảnh</th>`+
                `<th>Các môn học đăng ký</th>`+
                `<th>Trạng thái</th>`+
                `<th>Delete</th>`+
                `<th>Edit</th>`+
                `<th>view</th>`+
                '</tr>';
                if (students !== null && students.size !== 0){
                    for (let i = 0; i < students.length; i++) {
                        content += getStudent(students[i]);
                    }
                }
               content += "</table>";
                document.getElementById("listStudent").innerHTML = content;
        }
    })
}
function getStudent(student){
return  "<tr>" +
    `<td>${student.name}</td>` +
    `<td>${student.address}</td>` +
    `<td><img src="C:\\Users\\admin\\Desktop\\student-management\\src\\main\\webapp\\image\\ +${student.image}"></td>` +
    `<td><div id='listDetail'>`+ listDetail(student.id) +`</div></td>` +
    `<td>${student.status.name}</td>` +
    `<td><button onclick=''>Delete</button></td>` +
    `<td><button onclick=''>Edit</button></td>` +
    `<td><button onclick=''>View</button></td>` +
    "</tr>";
}
function listDetail(id_student) {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/detail/detail_student_and_subject/{id_student}",
        success: function (details){
           if (details !== null && details.size !== 0){
               for (let i = 0; i < details.length; i++) {
                   <p>${details.subject.name}</p>
               }
           }
        }
    })
}