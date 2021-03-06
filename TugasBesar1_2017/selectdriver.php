<?php
  require 'preliminarycheck.php';

	if ($_SERVER['REQUEST_METHOD'] == 'POST')
	{
    require 'connection.php';

    $pickup = $mysqli->real_escape_string($_POST['pickup']);
    $dest = $mysqli->real_escape_string($_POST['dest']);
    $preferredDriver = [];
    if (isset($_POST['pref']) && $_POST['pref'] != ""){
      $preferredDriver = explode(', ', $mysqli->real_escape_string($_POST['pref']));
    }
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
        <li class="order-active"><div class="circle">2</div>Select a Driver</li>
        <li class="list-order"><div class="circle">3</div>Complete Your Order</li>
      </ul>
  </div>

  <div id="order-page">
    <!-- PREFERRED DRIVERS -->
    <div class="preferred-driver">
      <p class="header-pref">PREFERRED DRIVERS:</p>

      <table>

        <?php
          if ($preferredDriver == NULL) { // empty preferred driver
            echo '<div class="nothing-driver">Nothing to display &#128514;</div>';
          } else {
            $driverExist = false;
            foreach ($preferredDriver as $name) {
              $query = "SELECT * from (SELECT id, fullname, img_path, star, vote from user WHERE is_driver=1 AND fullname='$name' AND id!='$_GET[id_active]') as U INNER JOIN preferredlocation as P ON U.id=P.id AND (P.location='$pickup' OR P.location='$dest') GROUP BY U.id";
              $result = $mysqli->query($query);

              if ($result->num_rows > 0) {
                $driverExist = true;
                $loopResult = "";
                while($row = $result->fetch_array()) {
                  $loopResult .=
                 '<form action="completeorder.php?id_active='.$_GET['id_active'].'" method="POST">
                    <tr>
                      <td rowspan="3"><img src='.$row['img_path'].' class="square-image"></td>
                      <td class="horizontal-space"></td>
                      <td class="horizontal-space"></td>
                      <td class="data-name">'.$row['fullname'].'</td>
                    </tr>
                    <tr>
                      <td class="horizontal-space"></td>
                      <td class="horizontal-space"></td>
                      <td class="data-rating"><font color="orange">&#9734; '.$row['star'].'</font> ('.$row['vote'].' votes)</td>
                    </tr>
                    <tr>
                      <td class="horizontal-space"></td>
                      <td class="horizontal-space"></td>

                      <input type="hidden" name="id_driver" value='.$row['id'].'>
                      <input type="hidden" name="pickup" value='.$pickup.'>
                      <input type="hidden" name="dest" value='.$dest.'>

                      <td>
                        <br>
                        <button class="button-choose">I CHOOSE YOU!</div>
                      </td>
                    </tr>
                  </form>';
                }
                echo $loopResult;
              }
            }
            if (!$driverExist){
              echo '<div class="nothing-driver">Nothing to display &#128514;</div>';
            }
          }
        ?>

      </table>
    </div>
    <br><br>
    <!-- OTHER DRIVERS -->
    <div class="preferred-driver">
      <p class="header-pref">OTHER DRIVERS:</p>
      <table>

        <?php
          $query = "SELECT * from (SELECT id, fullname, img_path, star, vote from user WHERE is_driver=1 AND id!='$_GET[id_active]') as U INNER JOIN preferredlocation as P ON U.id=P.id AND (P.location='$pickup' OR P.location='$dest') GROUP BY U.id";
          $result = $mysqli->query($query);
          if ($result->num_rows == 0) {
            echo '<div class="nothing-driver">Nothing to display &#128514;</div>';
          } else {
            $driverExist = false;
            while($row = $result->fetch_array()) {
              if (!in_array($row['fullname'], $preferredDriver)) {
                $driverExist = true;
                $loopResult = "";
                $loopResult .=
                '<form action="completeorder.php?id_active='.$_GET['id_active'].'" method="POST">
                  <tr>
                    <td rowspan="3"><img src='.$row['img_path'].' class="square-image"></td>
                    <td class="horizontal-space"></td>
                    <td class="horizontal-space"></td>
                    <td class="data-name">'.$row['fullname'].'</td>
                  </tr>
                  <tr>
                    <td class="horizontal-space"></td>
                    <td class="horizontal-space"></td>
                    <td class="data-rating"><font color="orange">&#9734; '.$row['star'].'</font> ('.$row['vote'].' votes)</td>
                  </tr>
                  <tr>
                    <td class="horizontal-space"></td>
                    <td class="horizontal-space"></td>

                  <input type="hidden" name="id_driver" value='.$row['id'].'>
                  <input type="hidden" name="pickup" value='.$pickup.'>
                  <input type="hidden" name="dest" value='.$dest.'>

                  <td>
                    <br>
                    <button class="button-choose">I CHOOSE YOU!</div>
                    </td>
                  </tr>
                </form>';

                echo $loopResult;
              }
            }
            if (!$driverExist){
              echo '<div class="nothing-driver">Nothing to display &#128514;</div>';
            }
          }
        ?>

      </table>
    </div>
  </div>
</body>
</html>
