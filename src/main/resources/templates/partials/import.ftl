<div class="container import">
    <h1>Import CSV</h1>
    Ajouter un fichier : <input type="file" file-model="ctx.csvFile"/>
    <select ng-model="fileType" ng-options="v as v for v in ctx.fileTypes"/>
    <button type="button" ng-click="import()" class="import-btn btn btn-primary">Lancer l'import !</button>
    <img src="/resources/img/loader.gif" ng-if="importing"/>
    <div ng-if="success > 0" class="alert alert-success" role="alert">Import réussi : {{success}} entrées au total</div>
</div>