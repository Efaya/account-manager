<#assign user = principal />
<#assign authorities = authorities />
<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link href="/resources/bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="/resources/bower_components/angular-chart.js/dist/angular-chart.css" rel="stylesheet" />
        <link href="/resources/css/footer.css" rel="stylesheet" />
        <link href="/resources/css/app.css" rel="stylesheet" />
        <title>Gestion des comptes - Home</title>
    </head>
    <body ng-app="accountManagerApp" ng-controller="AccountManagerController">
        <div id="wrap">
            <nav class="navbar navbar-default navbar-static-top">
                <div class="container">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="#/home">Gestion &amp; suivi des comptes</a>
                    </div>
                    <div id="navbar" class="navbar-collapse collapse">
                        <ul class="nav navbar-nav">
                            <li><a href="#/home">Home</a></li>
                            <li><a href="#/records">Mes données</a></li>
                            <li><a href="#/category">Categories</a></li>
                            <li><a href="#/import">Import</a></li>
                            <#if authorities?seq_contains("ADMIN")>
                                <li><a href="#/users">Utilisateurs</a></li>
                            </#if>
                        </ul>
                        <div class="nav navbar-nav navbar-right">
                            <form role="form" action="/logout" method="post" style="margin-top: 8px;">
                                <button type="submit" class="btn"><i class="glyphicon glyphicon-off"></i></button>
                            </form>
                        </div>
                        <div class="login nav navbar-nav navbar-right">
                            ${user}
                        </div>
                    </div>
                </div>
            </nav>
            <div ng-view="true"></div>
        </div>
        <div class="footer">
            <div class="container">
                <p class="text-muted">Réalisé par <a href="mailto:sahbi.ktifa@gmail.com">Sahbi Ktifa</a> - Powered by <img src="/resources/img/boot.png"/><img src="/resources/img/mongodb.png"/><img src="/resources/img/angular.png"/></p>
            </div>
        </div>
        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="/resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
        <script src="/resources/bower_components/Chart.js/Chart.js"></script>
        <script type="text/javascript" src="/resources/bower_components/angular/angular.min.js"></script>
        <script type="text/javascript" src="/resources/bower_components/angular-resource/angular-resource.min.js"></script>
        <script type="text/javascript" src="/resources/bower_components/angular-route/angular-route.min.js"></script>
        <script type="text/javascript" src="/resources/bower_components/angular-chart.js/dist/angular-chart.js"></script>
        <script type="text/javascript" src="/resources/app/app.js"></script>
        <script type="text/javascript" src="/resources/app/services.js"></script>
        <script type="text/javascript" src="/resources/app/filters.js"></script>
        <script type="text/javascript" src="/resources/app/directives.js"></script>
        <script type="text/javascript" src="/resources/app/controllers.js"></script>
    </body>
</html>