<?php
	require 'connection.php';
	if (isset($_POST['addloc'])) {
		$newloc = $mysqli->real_escape_string($_POST['newloc']);
		$id = $_GET['id_active'];
		$sql = ("INSERT INTO preferredlocation (id, location) VALUES ('$id', '$newloc')");
		if ($mysqli->query($sql) === true) {
			/*echo "<script>alert('New location added');</script>";*/
		} else {
			/*echo "<script>alert('Failed to add new location');</script>";*/
		}
	}
?>

<!DOCTYPE html>
<html>
<head>
	<link rel="icon" href="img/icon.png" />
	<title>Ojek Panas | Edit</title>
	<link rel="stylesheet" type="text/css" href="./css/style.css">
	<script type="text/javascript" src="./js/editlocation.js"></script>
    <script type="text/javascript" src="js/validation.js"></script>
</head>
<body>
	<div class="edit-title">
        <span>EDIT PREFERRED LOCATIONS</span>
    </div>
    <div id="edit-profile-content">
    	<div id="edit-location-list">
    		<table id="preferredlocation" class="border-table" width="550px">

		    	<tr>
		    		<th>No</th>
		    		<th>Location</th>
		    		<th colspan="2">Actions</th>
		    	</tr>
		    	<?php
		    	    require 'connection.php';
		    	    $id = $_GET['id_active'];
					$sql = "SELECT * FROM preferredlocation WHERE id=$id";
					$result = $mysqli->query($sql);

					if ($result->num_rows > 0) {
						$loopResult = '';
						$counter = 1;
						// output data of each row
						while($row = $result->fetch_assoc()) {
							$loopResult .= '<tr id="tabel'.$row['location'].'">
					    		<td>'.$counter.'</td>
					    		<td id="data'.$row['location'].'">'.$row['location'].'</td>
					    		<td class="pencil-image"><a href="javascript:" id="'.$row['location'].'" name="pencil" class="pencil" onclick="return editdata(this.id, this.name)"><img id="image'.$row['location'].'" width="20px" height="20px"  src="img/pencil.png"></a>
                                    <center><a href="javascript:" id="save'.$row['location'].'" name="'.$id.'" class="functionalsave" onclick="return savedata(this.id, this.name)"><img id="imagefunc'.$row['location'].'" width="20px" height="20px"  src="img/save.png" style="display:none;"></a></center>
                                    </td>
					    		<td class="cancel-image"><a href="delete.php?id_active='.$id.'&loc='.$row['location'].'" class="confirmation" onclick="return confirm_delete()"><img src="img/cancel.png" width="20px" height="20px"></a></td>
                                
                            </tr>';
                            $counter++;
                        }
					    echo $loopResult;
					} else {
						echo "<td colspan='4' class='nothing'>Nothing to display &#128514;</td>";
					}
					$mysqli->close();
				?>
		    </table>
		    <div class="small-empty-space"></div>
    		<div class="small-title">
        		<span>ADD NEW LOCATIONS:</span>
    		</div>
    		<form method="POST" action="editlocation.php?id_active=<?=$_GET['id_active']?>" onsubmit="return validateAddLocation()">
    			<table width="550px">
    				<tr>
    					<td>
    						<input class="text-field" type="text" name="newloc" id="newloc">
    					</td>
    					<td><button class="save-button" name="addloc">ADD</button></td>
    				</tr>
    			</table>

    		</form>
    		<div class="small-empty-space"></div>
    		<input type="button" class="back-button" value="BACK" onclick="window.location.href='profile.php?id_active=<?php echo $_GET['id_active']; ?>'">
    	</div>

    </div>

</body>
</html>

<!-- <script type="text/javascript" language="javascript">
	$(document).on('click', '.cancel', function() {

	});
	/*$(document).on('click', '.delete', function(){
   var id = $(this).attr("id");
   if(confirm("Are you sure you want to remove this?"))
   {
    $.ajax({
     url:"delete.php",
     method:"POST",
     data:{id:id},
     success:function(data){
      $('#alert_message').html('<div class="alert alert-success">'+data+'</div>');
      $('#user_data').DataTable().destroy();
      fetch_data();
     }
    });
    setInterval(function(){
     $('#alert_message').html('');
    }, 5000);
   }
  });
 });*/
</script> -->
