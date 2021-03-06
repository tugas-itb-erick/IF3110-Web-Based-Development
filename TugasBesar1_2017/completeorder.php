<?php
  require 'preliminarycheck.php';

	if ($_SERVER['REQUEST_METHOD'] == 'POST')
	{
    require 'connection.php';

    $pickup = $mysqli->real_escape_string($_POST['pickup']);
    $dest = $mysqli->real_escape_string($_POST['dest']);
    $id_driver = $mysqli->real_escape_string($_POST['id_driver']);

    $query = "SELECT * from USER where id='$id_driver'";
    $result = $mysqli->query($query);

    if (!$result) {
      exit("The query failed!");
    }

    $user = $result->fetch_assoc();
    $img_path = $user['img_path'];
    $username = $user['username'];
    $fullname = $user['fullname'];
	}
?>

<!DOCTYPE html>
<html>
<head>
  <link rel="icon" href="img/icon.png" />
  <title>Ojek Panas | Order</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
  <div id="navbar">
    <?php include("navbar.php"); ?>
    <div class="after-box">
      <div class="centered">
        <a href="order.php?id_active=<?php echo $_GET['id_active']; ?>" class="active-order">ORDER
        <a href="historyorder.php?id_active=<?php echo $_GET['id_active']; ?>" class="list-item-history">HISTORY
        <a href="profile.php?id_active=<?php echo $_GET['id_active']; ?>" class="list-item-profile">MY PROFILE</a>
      </div>
    </div>
  </div>

  <div id="order-header">
    <div class="floating-box-left-mo">MAKE AN ORDER</div>
      <ul class="list-centered">
        <li class="list-order"><div class="circle">1</div>Select Destination</li>
        <li class="list-order"><div class="circle">2</div>Select a Driver</li>
        <li class="order-active"><div class="circle">3</div>Complete Your Order</li>
      </ul>
  </div>

  <form action="finishorder.php?id_active=<?php echo $_GET['id_active']; ?>" method="POST" onsubmit="return starValidation()">
    <div id="order-content">
      <div class="floating-box-left-o">HOW WAS IT?</div><br><br><br><br><br>
      <img class="picture-o" src="<?=$img_path?>">
      <p class="username">@<?=$username?></p>
      <p class="data"><?=$fullname?></p>
      <div class="star">
        <input class="star star-5" id="star-5" type="radio" name="star" value="5"/>
        <label class="star star-5" for="star-5"></label>
        <input class="star star-4" id="star-4" type="radio" name="star" value="4"/>
        <label class="star star-4" for="star-4"></label>
        <input class="star star-3" id="star-3" type="radio" name="star" value="3"/>
        <label class="star star-3" for="star-3"></label>
        <input class="star star-2" id="star-2" type="radio" name="star" value="2"/>
        <label class="star star-2" for="star-2"></label>
        <input class="star star-1" id="star-1" type="radio" name="star" value="1"/>
        <label class="star star-1" for="star-1"></label>
      </div>
    </div>

    <input type="hidden" name="id_driver" value=<?=$id_driver?>>
    <input type="hidden" name="pickup" value=<?=$pickup?>>
    <input type="hidden" name="dest" value=<?=$dest?>>

    <div id="comment-page">
      <textarea class="input-text-long" type="text" name="comment" id="comment" maxlength="200" placeholder=" Your Comment..."></textarea><br>
      <button class="button" type="submit">COMPLETE ORDER</button>
    </div>
  </form>

  <script src="js/validation.js"></script>
</body>
</html>
