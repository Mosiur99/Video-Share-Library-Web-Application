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
<h2>Discuss Implementation Part:</h2>
<ul>
  <li>
    <p><strong>New User Register: </strong>We have a signup page where a user creates a new account. Here a user creates an account with the information of username, email, and password.</p>
    <p><strong>Notes: </strong>The important part of the signup page is the user must use an email whose email is not already registered in this web application.</p>
  </li>
  <li>
    <p><strong>User Login: </strong>After creating an account the user can log in to the web application page. Here user can log in with his email and password which email and password he provided at the time of registration. If the email and password are provided incorrectly he can not access the web page.</p>
    <p><strong>Notes: </strong>By Default, in spring boot security a user can log in with his username and password. But on our webpage, we build the login system with the user's email and password.</p>
  </li>
</ul>

