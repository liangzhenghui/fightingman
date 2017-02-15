<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java"  import="ums.service.UserManager"%>
<%@ page language="java"  import="ums.model.User"%>
<%String contextPath = request.getContextPath();%>
<%String productId = (String)request.getParameter("id"); %>
<!DOCTYPE html>
<html lang="en"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>产品详细页面</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
	<link rel="stylesheet" href="<%=contextPath%>/css/base.css"/>
	<link rel="stylesheet" href="<%=contextPath%>/css/index.css"/>
    <!-- Le styles -->
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/library/bootstrap/css/bootstrap.min.css">
    <link href="<%=contextPath%>/library/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
	<script src="<%=contextPath%>/library/js/jquery/jquery-1.11.1.min.js" type="text/javascript"></script>
    <style>

    /* GLOBAL STYLES
    -------------------------------------------------- */
    /* Padding below the footer and lighter body text */

    body {
      padding-bottom: 40px;
      color: #5a5a5a;
    }



    /* CUSTOMIZE THE NAVBAR
    -------------------------------------------------- */

    /* Special class on .container surrounding .navbar, used for positioning it into place. */
    .navbar-wrapper {
      position: absolute;
      top: 0;
      left: 0;
      right: 0;
      z-index: 10;
      margin-top: 20px;
      margin-bottom: -90px; /* Negative margin to pull up carousel. 90px is roughly margins and height of navbar. */
    }
    .navbar-wrapper .navbar {

    }

    /* Remove border and change up box shadow for more contrast */
    .navbar .navbar-inner {
      border: 0;
      -webkit-box-shadow: 0 2px 10px rgba(0,0,0,.25);
         -moz-box-shadow: 0 2px 10px rgba(0,0,0,.25);
              box-shadow: 0 2px 10px rgba(0,0,0,.25);
    }

    /* Downsize the brand/project name a bit */
    .navbar .brand {
      padding: 14px 20px 16px; /* Increase vertical padding to match navbar links */
      font-size: 16px;
      font-weight: bold;
      text-shadow: 0 -1px 0 rgba(0,0,0,.5);
    }

    /* Navbar links: increase padding for taller navbar */
    .navbar .nav > li > a {
      padding: 15px 20px;
    }

    /* Offset the responsive button for proper vertical alignment */
    .navbar .btn-navbar {
      margin-top: 10px;
    }



    /* CUSTOMIZE THE CAROUSEL
    -------------------------------------------------- */

    /* Carousel base class */
    .carousel {
      margin-bottom: 60px;
    }

    .carousel .container {
      position: relative;
      z-index: 9;
    }

    .carousel-control {
      height: 80px;
      margin-top: 0;
      font-size: 120px;
      text-shadow: 0 1px 1px rgba(0,0,0,.4);
      background-color: transparent;
      border: 0;
      z-index: 10;
    }

    .carousel .item {
      height: 500px;
    }
    .carousel img {
      position: absolute;
      top: 0;
      left: 0;
      min-width: 100%;
      height: 500px;
    }

    .carousel-caption {
      background-color: transparent;
      position: static;
      max-width: 550px;
      padding: 0 20px;
      margin-top: 200px;
    }
    .carousel-caption h1,
    .carousel-caption .lead {
      margin: 0;
      line-height: 1.25;
      color: #fff;
      text-shadow: 0 1px 1px rgba(0,0,0,.4);
    }
    .carousel-caption .btn {
      margin-top: 10px;
    }



    /* MARKETING CONTENT
    -------------------------------------------------- */

    /* Center align the text within the three columns below the carousel */
    .marketing .span4 {
      text-align: center;
    }
    .marketing h2 {
      font-weight: normal;
    }
    .marketing .span4 p {
      margin-left: 10px;
      margin-right: 10px;
    }


    /* Featurettes
    ------------------------- */

    .featurette-divider {
      margin: 80px 0; /* Space out the Bootstrap <hr> more */
    }
    .featurette {
      padding-top: 120px; /* Vertically center images part 1: add padding above and below text. */
      overflow: hidden; /* Vertically center images part 2: clear their floats. */
    }
    .featurette-image {
      margin-top: -120px; /* Vertically center images part 3: negative margin up the image the same amount of the padding to center it. */
    }

    /* Give some space on the sides of the floated elements so text doesn't run right into it. */
    .featurette-image.pull-left {
      margin-right: 40px;
    }
    .featurette-image.pull-right {
      margin-left: 40px;
    }

    /* Thin out the marketing headings */
    .featurette-heading {
      font-size: 50px;
      font-weight: 300;
      line-height: 1;
      letter-spacing: -1px;
    }



    /* RESPONSIVE CSS
    -------------------------------------------------- */

    @media (max-width: 979px) {

      .container.navbar-wrapper {
        margin-bottom: 0;
        width: auto;
      }
      .navbar-inner {
        border-radius: 0;
        margin: -20px 0;
      }

      .carousel .item {
        height: 500px;
      }
      .carousel img {
        width: auto;
        height: 500px;
      }

      .featurette {
        height: auto;
        padding: 0;
      }
      .featurette-image.pull-left,
      .featurette-image.pull-right {
        display: block;
        float: none;
        max-width: 40%;
        margin: 0 auto 20px;
      }
    }


    @media (max-width: 767px) {

      .navbar-inner {
        margin: -20px;
      }

      .carousel {
        margin-left: -20px;
        margin-right: -20px;
      }
      .carousel .container {

      }
      .carousel .item {
        height: 300px;
      }
      .carousel img {
        height: 300px;
      }
      .carousel-caption {
        width: 65%;
        padding: 0 70px;
        margin-top: 100px;
      }
      .carousel-caption h1 {
        font-size: 30px;
      }
      .carousel-caption .lead,
      .carousel-caption .btn {
        font-size: 18px;
      }

      .marketing .span4 + .span4 {
        margin-top: 40px;
      }

      .featurette-heading {
        font-size: 30px;
      }
      .featurette .lead {
        font-size: 18px;
        line-height: 1.5;
      }

    }
    </style>
 </head>

  <body style="font-family:'Microsoft JhengHei'">
	<div class="header">
		<ul class="nav" id="lb">
			<!-- <li class="logo"></li> -->
			<li><a style="background-color:transparent" href="<%=contextPath%>/main.jsp">首页</a></li>
			<!-- <li><a href="#">Mac</a></li>
			<li><a href="#">iPhone</a></li>
			<li><a href="#">Watch</a></li>
			<li><a href="#">iPad</a></li>
			<li><a href="#">iPod</a></li>
			<li><a href="#">iTunes</a></li>
			<li><a href="#">技术支持</a></li>
			<li class="search">
				<input placeholder="搜索"/>
			</li> -->
		</ul>
	</div>
    <!-- Carousel
    ================================================== -->
  <!--   <div id="myCarousel" class="carousel slide">
      <div class="carousel-inner">
        <div class="item">
          <img src="./Carousel Template · Bootstrap_files/slide-01.jpg" alt="">
          <div class="container">
            <div class="carousel-caption">
              <h1>Example headline.</h1>
              <p class="lead">Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
              <a class="btn btn-large btn-primary" href="http://v2.bootcss.com/examples/carousel.html#">Sign up today</a>
            </div>
          </div>
        </div>
        <div class="item">
          <img src="./Carousel Template · Bootstrap_files/slide-02.jpg" alt="">
          <div class="container">
            <div class="carousel-caption">
              <h1>Another example headline.</h1>
              <p class="lead">Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
              <a class="btn btn-large btn-primary" href="http://v2.bootcss.com/examples/carousel.html#">Learn more</a>
            </div>
          </div>
        </div>
        <div class="item active">
          <img src="./Carousel Template · Bootstrap_files/slide-03.jpg" alt="">
          <div class="container">
            <div class="carousel-caption">
              <h1>One more for good measure.</h1>
              <p class="lead">Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
              <a class="btn btn-large btn-primary" href="http://v2.bootcss.com/examples/carousel.html#">Browse gallery</a>
            </div>
          </div>
        </div>
      </div>
      <a class="left carousel-control" href="http://v2.bootcss.com/examples/carousel.html#myCarousel" data-slide="prev">‹</a>
      <a class="right carousel-control" href="http://v2.bootcss.com/examples/carousel.html#myCarousel" data-slide="next">›</a>
    </div> --><!-- /.carousel -->



    <!-- Marketing messaging and featurettes
    ================================================== -->
    <!-- Wrap the rest of the page in another container to center all the content. -->
<br><br><br><br><br>
    <div class="container marketing">
    	<h1 id="product_name"></h1>
    	<br><br><br>
    	<p id="product_description" class="lead"></p>
		<div id="product_detail"></div>
      <footer>
        <p class="pull-right"><a href="javascript:window.scrollTo(0,0);">回到顶部</a></p>
        <!-- <p>© 2013 Company, Inc. · <a href="#">Privacy</a> · <a href="http://v2.bootcss.com/examples/carousel.html#">Terms</a></p> -->
      </footer>

    </div><!-- /.container -->



    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
   <!--  <script src="./Carousel Template · Bootstrap_files/jquery.js"></script>
    <script src="./Carousel Template · Bootstrap_files/bootstrap-transition.js"></script>
    <script src="./Carousel Template · Bootstrap_files/bootstrap-alert.js"></script>
    <script src="./Carousel Template · Bootstrap_files/bootstrap-modal.js"></script>
    <script src="./Carousel Template · Bootstrap_files/bootstrap-dropdown.js"></script>
    <script src="./Carousel Template · Bootstrap_files/bootstrap-scrollspy.js"></script>
    <script src="./Carousel Template · Bootstrap_files/bootstrap-tab.js"></script>
    <script src="./Carousel Template · Bootstrap_files/bootstrap-tooltip.js"></script>
    <script src="./Carousel Template · Bootstrap_files/bootstrap-popover.js"></script>
    <script src="./Carousel Template · Bootstrap_files/bootstrap-button.js"></script>
    <script src="./Carousel Template · Bootstrap_files/bootstrap-carousel.js"></script>
    <script src="./Carousel Template · Bootstrap_files/bootstrap-typeahead.js"></script> -->
    <script src="<%=contextPath%>/library/bootstrap/js/bootstrap-collapse.js" type="text/javascript"></script>
    <script>
    $(function(){
    	initProductBaseInfo();
    	initProductImages();
    });
    function initProductBaseInfo(){
    	var id = '<%=productId%>';
    	var url = "<%=contextPath%>/get-product-by-id.json";
   	 $.post(url,{id:id},function(json){
  		  var data = json.product;
  		  if(data){
  			  var product_name = data.product_name;
  			  var product_description = data.product_description;
  			  $("#product_name").text(product_name);
  			 $("#product_description").text(product_description);
  		  }
  		 });
    }
    function initProductImages(){
    	var id = '<%=productId%>';
    	var url = "<%=contextPath%>/get-images-of-product.json";
    	 $.post(url,{id:id},function(json){
   		  var data = json.list;
   		  if(data){
   		  	 for(var i = 0; i < data.length;i++){
   		  	 var hr = $("<hr class='featurette-divider'>");
			 var div = $("<div class='featurette'></div>");
			 var img;
			 if(i%2==0){
				 img = $("<img class='featurette-image pull-left' src=''>");
			 }else{
				 img = $("<img class='featurette-image pull-right' src=''>");
			 }
			 img.attr("src",'<%=contextPath%>/img.json?imgId='+data[i].id);
			 var title = data[i].title?data[i].title:"";
			 var description = data[i].description?data[i].description:"";
			 var h2 = $("<h2 class='featurette-heading'>"+title+"</h2>");
   	         var p = $("<p class='lead'>"+description+"</p>");
   	         div.append(img).append(h2).append(p);
   	         $("#product_detail").append(hr);
   	         $("#product_detail").append(div);
   		  	 }
   		  }
   		 });
    }
    </script>
</body></html>