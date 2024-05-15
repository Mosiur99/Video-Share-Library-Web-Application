# Video-Share-Library-Web-Application
<h3>Project Requirement:</h3>
<ul>
  <li>
    In this web application, any user can share YouTube videos & anyone can watch & like videos.
  </li>
  <li>
    Without Login anyone will only see the homepage, which contains all the videos and users can watch videos from our site, & the view count will increase.
  </li>
  <li>
    Without Login anyone will only see the homepage, which contains all the videos and users can watch videos from our site, & the view count will increase, but With login users can like/dislike the video.
  </li>
  <li>
    <p>Each video will show the <strong>Total view count</strong>, <strong>Like count</strong>, <strong>Dislike count</strong>, <strong>Details button</strong>(after clicking this button user can see the list of liked and disliked users and also can see the video uploader name)</p>
  </li>
  <li>
    Each user has a dashboard. In this dashboard <strong>He will grant access to add YouTube videos</strong>, <strong>He can also view the videos he uploaded</strong>, 
    <strong>Also can edit the video</strong>
  </li>
  <li>
    <p><strong>Notes: </strong>The user can not like or dislike his videos.</p>
  </li>
</ul>
<h3>Stack</h3>
<ul>
  <li>Java</li>
  <li>Spring Boot</li>
  <li>Ajax</li>
  <li>Thymeleaf</li>
  <li>MySQL</li>
</ul>
<h3>Discuss Implementation Part:</h3>
<ul>
  <li>
    <p><strong>New User Register: </strong>We have a signup page where a user creates a new account. Here a user creates an account with the information of username, email, and password.</p>
    <p><strong>Notes: </strong>The important part of the signup page is the user must use an email whose email is not already registered in this web application.</p>
    ![download](https://github.com/Mosiur99/Video-Share-Library-Web-Application/assets/143164282/342ffa76-9189-45ce-98c7-63bcfe1dd6ba)
  </li>
  <li>
    <p><strong>User Login: </strong>After creating an account the user can log in to the web application page. Here user can log in with his email and password which email and password he provided at the time of registration. If the email and password are provided incorrectly he can not access the web page.</p>
    <p><strong>Notes: </strong>By Default, in spring boot security a user can log in with his username and password. But on our webpage, we build the login system with the user's email and password.</p>
  </li>
  <li>
    <p><strong>User Dashboard: </strong>After login, a user is redirected to his own dashboard. This dashboard has a video list of all videos uploaded by the user. We also have an edit option for all videos where the user can change the video information like - video URL and video Title.</p>
  </li>
  <li>
    <p><strong>Add New Video: </strong>Here a login user can upload a new video with information about the video URL and and video title. 
    <p><strong>Notes: </strong>A user can not upload a video in which the video is already uploaded by other users.</p>
  </li>
  <li>
    <p><strong>Single Video Details: </strong>Here we have the details about a single video. When a user comes to this page the video is automatically started and the video view count is incremented even if the video is uploaded by the user the video view count is also incremented. Here we have also three buttons Like, Dislike, and Details. If a user presses the Like/Dislike button here 6 happens can occur.</p>
    <ul>
      <li>
        If a user presses the Like button and the user has already previously liked this video then the like count will decrease and the user will be removed from the list of Liked Users.
      </li>
      <li>
        If a user presses the Like button and the user has already previously disliked this video then the like count will increase then the user will be added to the list of Liked users and the disliked count will decrease then the user will be removed from the list of Disliked Users.
      </li>
      <li>
        If a user presses the Like button and the user does not exist in both the list of liked users and disliked users then increase the like count and add the user to the list of liked users.
      </li>
      <li>
        If a user presses the Dislike button and the user has already previously disliked this video then the dislike count will decrease and the user will be removed from the list of Disliked Users.
      </li>
      <li>
        If a user presses the Dislike button and the user has already previously liked this video then the dislike count will increase then the user will be added to the list of Disliked users and the liked count will decrease then the user will be removed from the list of Liked Users.
      </li>
      <li>
        If a user presses the Dislike button and the user does not exist in both the list of liked users and disliked users then increase the dislike count and add the user to the list of disliked users.
      </li>
    </ul>
    <p><strong>Note: </strong>The user can not like or dislike his videos.</p>
    <p>If a user presses the Details button then he will show the name of the user who uploaded this video and also show the list of liked users and disliked users.</p>
  </li>
  <li>
    <p><strong>Home Page: </strong>The home page has a list of all videos which videos are uploaded by all users.
Without login, any user can see this page and also view the video and the view count of the video will increase accordingly.
</p>
  </li>
</ul>

