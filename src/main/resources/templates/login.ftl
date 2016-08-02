<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="/resources/bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/resources/css/signin.css" rel="stylesheet" />
    <link href="/resources/css/footer.css" rel="stylesheet" />
    <title>Gestion des comptes - Login</title>
</head>
<body ng-app="registerManagerApp">
    <div id="wrap">
        <div class="container">
            <#if error.isPresent()>
                <div class="alert alert-danger">Nom d'utilisateur inconnu, mot de passe incorrect ou l'utilisateur n'a pas été validé.</div>
            </#if>
            <#if logout.isPresent()>
                <div class="alert alert-info">Vous avez été déconnecté.</div>
            </#if>

            <form role="form" action="/login" method="post" class="form-signin">
                <h2 class="form-signin-heading">Connexion</h2>
                <label for="inputUsername" class="sr-only">Username</label>
                <input type="text" id="inputUsername" name="username" class="form-control" placeholder="Nom d'utilisateur" required="" autofocus=""/>
                <label for="inputPassword" class="sr-only">Password</label>
                <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Mot de passe" required="" />

                <button class="btn btn-lg btn-primary btn-block" type="submit">Se connecter</button>
            </form>
            <div class="form-register" ng-controller="RegistrationCtrl">
                <div ng-if="registrationSuccess" class="alert alert-success" role="alert">Enregistrement réussi, vous allez recevoir un email de confirmation.</div>
                <div ng-if="registrationError" class="alert alert-danger" role="alert">{{registrationErrorMsg}}</div>
                <form name="registerForm" class="form-horizontal" ng-submit="registration()">
                    <fieldset>
                        <a ng-click="showRegisterForm = !showRegisterForm">S'enregistrer ?</a>
                        <div ng-if="showRegisterForm">
                            <div class="control-group">
                                <!-- Username -->
                                <label class="control-label"  for="register-username">Nom d'utilisateur</label>
                                <div class="controls">
                                    <input type="text" pattern=".{4,}" id="register-username" ng-model="register.username" name="register-username" placeholder="Saisissez votre nom d'utilisateur" class="form-control" required="required" />
                                </div>
                            </div>

                            <div class="control-group">
                                <!-- E-mail -->
                                <label class="control-label" for="register-email">E-mail</label>
                                <div class="controls">
                                    <input type="email" ng-model="register.email" name="register-email" id="register-email" placeholder="Saisissez votre email" class="form-control" required="required"/>
                                </div>
                            </div>

                            <div class="control-group">
                                <!-- Password-->
                                <label class="control-label" for="register-password">Mot de passe</label>
                                <div class="controls">
                                    <input type="password" pattern=".{4,}" ng-model="register.password" name="register-password" id="register-password" placeholder="Saisissez votre mot de passe" class="form-control" required="required"/>
                                </div>
                            </div>

                            <div class="control-group">
                                <!-- Password -->
                                <label class="control-label"  for="register-password_confirm">Mot de passe (Confirmation)</label>
                                <div class="controls">
                                    <input type="password" pattern=".{4,}" ng-model="register.confirm" name="register-password_confirm" id="register-password_confirm" placeholder="Confirmez votre mot de passe" class="form-control" required="required"/>
                                </div>
                            </div>

                            <div class="control-group">
                                <!-- Button -->
                                <div class="controls">
                                    <button type="submit" ng-disabled="!registerForm.$valid || (register.confirm != undefined &amp;&amp; register.confirm !== register.password)" class="btn btn-success">S'enregistrer</button>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
    <div class="footer">
        <div class="container">
            <p class="text-muted">Réalisé par <a href="mailto:sahbi.ktifa@gmail.com">Sahbi Ktifa</a> - Powered by <img src="/resources/img/boot.png"/><img src="/resources/img/mongodb.png"/><img src="/resources/img/angular.png"/></p>
        </div>
    </div> <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="/resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/resources/bower_components/angular/angular.min.js"></script>
    <script type="text/javascript" src="/resources/bower_components/angular-resource/angular-resource.min.js"></script>
    <script type="text/javascript" src="/resources/app/register/app.js"></script>
    <script type="text/javascript" src="/resources/app/register/services.js"></script>
    <script type="text/javascript" src="/resources/app/register/controllers.js"></script>

</body>
</html>