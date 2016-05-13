<div class="records-block col-md-12">
    <ul class="col-md-12">
        <li ng-if="records.length == 0" class="col-md-12" >
            <h3 class="no-data">Vous n'avez aucune donnée disponible</h3>
        </li>
        <li ng-if="records.length > 0" class="col-md-12" ng-repeat="year in years">
            <h2>Année {{year}}</h2>
            <ul class="col-md-12">
                <li class="col-md-12" ng-repeat="month in months | reverse">
                    <ul class="col-md-12" ng-if="records[year][month].length > 0">
                        <li class="records-list" ng-click="showRecords = !showRecords">
                            <h3>{{month}} : {{records[year][month].length}} entrées <i ng-if="showRecords" class="glyphicon glyphicon-chevron-down"/><i ng-if="!showRecords" class="glyphicon glyphicon-chevron-right"/></h3>
                        </li>
                        <li ng-if="showRecords" class="entry col-md-6" ng-repeat="record in records[year][month] | orderBy:'date'">
                            <div class="col-md-8">
                                <span>{{record.date | date:'dd/MM/yyyy'}}</span>
                                <span><b>{{record.label}}</b></span>
                                <span>{{record.type | uppercase}}</span>
                                <span ng-class="{'revenue':record.value > 0, 'depense':record.value < 0}">{{record.value}} €</span>
                                <span>{{record.category | categoryName:this}}</span>
                                <button type="button" ng-if="record.category && record.category.length > 0 && !isLabelInCategory(record)" ng-click="addLabelToCategory(record)" class="btn"><i class="glyphicon glyphicon-plus"/></button>
                            </div>
                            <select class="col-md-2" ng-change="updateCategory(record)" ng-model="record.category" ng-options="category.id as category.label for category in categories | orderBy:'label'" ng-selected="category.id === record.category"></select>
                            <button type="button" ng-click="removeRecord(record)" class="col-md-2 btn btn-danger">Supprimer</button>
                        </li>
                    </ul>
                </li>
            </ul>
        </li>
    </ul>
</div>