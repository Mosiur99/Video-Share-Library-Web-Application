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
<h3>Discuss Implementation Part:</h3>
<ul>
  <li>
    <p><strong>New User Register: </strong>We have a signup page where a user creates a new account. Here a user creates an account with the information of username, email, and password.</p>
    <p><strong>Notes: </strong>The important part of the signup page is the user must use an email whose email is not already registered in this web application.</p>
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
</ul>

