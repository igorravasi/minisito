
		<div class="container">
			<nav class="navbar navbar-default">
				<div class="navbar-header">
			    	
			    	<a class="navbar-brand" href="MyMemo.html">MyMemo</a>
				
					<div class="navbar-collapse">
						<ul class="nav navbar-nav">
							<li class="dropdown">
								<a href="#" onclick="dropDown(this.parentNode)"  class="dropdown-toggle">Gioca<span class="caret"></span></a>
								<ul class="dropdown-menu">
									<li><a href="PartitaSingle.html">Single player</a></li>
									<li><a href="PartitaMulti.html">Multiplayer</a></li>
								</ul>		
							<li><a href="Istruzioni.html">Info</a></li>
							<li><a href="Contatti.html">Contatti</a></li>

							<li class="dropdown">
								 <a href="#" onclick="dropDown(this.parentNode);document.getElementById('login_message').innerHTML=''" class="dropdown-toggle"><span id="user_name">Login</span> <b class="caret"></b></a>
								 <ul class="dropdown-menu" style="padding: 15px;min-width: 250px;">
									<li>
										<div class="row">
											<div class="col-md-12">
												<form class="form" role="form" id="login-nav" onsubmit="event.preventDefault(); return false">
													<div class="form-group">
														<label class="sr-only" for="id_user">Utente</label>
														<input type="text" class="form-control" name="Utente" id="id_user" placeholder="Utente">
													</div>
														<div class="form-group">
															<label class="sr-only" for="id_password">Password</label>
															<input type="password" class="form-control" name="Password" id="id_password" placeholder="Password">
														</div>
													
														<div class="form-group">
															<button class="btn btn-success btn-block" onclick="submitLogin(this)" name="Do" value="login">Login</button>
														</div>
														<div class="form-group">
															<button class="btn btn-success btn-block" onclick="submitLogin(this)" name="Do" value="logout">Logout</button>
														</div>
														
														<div class="form-group" id="login_message">
													  
														</div>
												</form>
											</div>
										</div>
									</li>
								 </ul>
							</li>
						</ul>
						<ul id ="multimenu" class="nav navbar-nav nascosto">

							<li><a href="Notifiche.html">Notifiche</a></li>
						</ul>	
						<ul class="nav navbar-bar">
								<li style="float:right"><button onclick="start();" id="start_button" class="control-button nascosto" >Start</button></li>
						</ul>

					</div>
			  </div>
			</nav>
		</div>