/**
 * 
 */
 var admin = false;
 var adminElement = document.getElementById("admin");
 adminElement.onclick = function () { showLogin();} 
 var login = document.getElementById("login");
 function showLogin() {
 	admin = !admin;
 	if (admin) {
		login.style.display = "block";
		console.log(login.display);
		return 0;
	}
	login.style.display = "none";
}