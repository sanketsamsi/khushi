<?php
 
mysql_connect("jdbc:mysql://localhost:3306/apptest","root","admin@123");//change server name  //pass username according your settings

mysql_select_db("apptest");// also chang the Mysql database name
 
$sql1=mysql_query("select * from user ");
 
if (!$sql1) {
 
echo "Could not successfully run query ($sql) from DB: " . mysql_error();
 
exit;
 
}
 
while($row=mysql_fetch_assoc($sql1))
 
$output[]=$row;
 
  
 
print(json_encode($output));// this will print the output in json
 
mysql_close();
 
?>