function likeVideo() {
    var id = /*[[${video.id}]]*/ '';
    var videoId = /*[[${video.videoId}]]*/ '';
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/user/likeOrDislike/' + videoId + '/' + id + '/LIKE', true);
    xhr.onload = function () {
        if (xhr.status === 200) {
            var responseDTO = JSON.parse(xhr.responseText);
            document.getElementById('likeCount').innerText = responseDTO.likeCount;
            document.getElementById('dislikeCount').innerText = responseDTO.dislikeCount;

            var likedUsers = responseDTO.likedUsers;
            var dislikedUsers = responseDTO.dislikedUsers;
            var list = document.getElementById('actionList');
            list.innerText = '';
            var paragraph = document.createElement('li');
            paragraph.innerText = "Liked User List";
            list.appendChild(paragraph);
            if(likedUsers.length === 0){
                var paragraph = document.createElement('li');
                paragraph.innerText = 'No user have';
                list.appendChild(paragraph);
            }
            for(var i in likedUsers){
                var user = document.createElement('li');
                user.innerText = likedUsers[i].username;
                list.appendChild(user);
            }
            var paragraph2 = document.createElement('li');
            paragraph2.innerText = "Disliked User List";
            list.appendChild(paragraph2);
            if(dislikedUsers.length === 0){
                var paragraph = document.createElement('li');
                paragraph.innerText = 'No user have';
                list.appendChild(paragraph);
            }
            for(var i in dislikedUsers){
                var user = document.createElement('li');
                user.innerText = dislikedUsers[i].username;
                list.appendChild(user);
            }

            document.getElementById('actionList').style.display = 'none';
        }
    };
    xhr.send();
}