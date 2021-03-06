<div class="container-fluid home">
    <div class="col-lg-12 row infos">
        <h2>Entrée du mois : {{months[monthlyResponse.month - 1]}} {{monthlyResponse.year}} ({{monthlyResponse.records.length}} entrées)</h2>
        <a ng-click="viewPastMonth()" >Aller au mois précédent</a><span ng-if="pastMonths > 1"> | <a ng-click="viewNextMonth()" >Revenir au mois de {{months[monthlyResponse.month]}}</a></span>
        <div>Résultat du mois : <span ng-class="{'revenue':monthlyResponse.sumIncomes - monthlyResponse.sumOutcomes > 0 , 'depense':monthlyResponse.sumIncomes - monthlyResponse.sumOutcomes < 0}">{{monthlyResponse.sumIncomes - monthlyResponse.sumOutcomes | number:2}}</span></div>
        <h3 class="no-data" ng-if="monthlyResponse.records.length == 0">Aucune donnée pour ce mois</h3>
    </div>
    <div class="col-lg-6" ng-if="monthlyResponse.records.length > 0">
        <h2>Dépenses : -{{monthlyResponse.sumOutcomes | number:2}}</h2>
        <canvas id="bar-outcome" class="chart chart-bar"
                chart-data="dataOutcomes" chart-labels="labels" chart-click="viewOutcomesDetails">
        </canvas>
    </div>
    <div class="col-lg-6" ng-if="monthlyResponse.records.length > 0">
        <h2>Revenus : +{{monthlyResponse.sumIncomes | number:2}}</h2>
        <canvas id="bar-income" class="chart chart-bar"
                chart-data="dataIncomes" chart-labels="labels" chart-click="viewIncomesDetails">
        </canvas>
    </div>
    <div class="col-lg-6" ng-if="selectedCategory !== false">
        <h2>Détails des entrées : {{selectedCategoryLabel}} (<span ng-class="{'revenue':selectedCategorySum.indexOf('-') == -1, 'depense':selectedCategorySum.indexOf('-') > -1}">{{selectedCategorySum | number:2}}</span>)</h2>
        <ul>
            <li class="entry col-lg-12" ng-repeat="record in selectedCategoryValues | orderBy:'date'" ng-if="record.category === selectedCategory">
                <span>{{record.date | date:'dd/MM/yyyy'}}</span>
                <span><b>{{record.label}}</b></span>
                <span>{{record.type | uppercase}}</span>
                <span ng-class="{'revenue':record.value > 0, 'depense':record.value < 0}">{{record.value | number:2}} €</span>
            </li>
        </ul>
    </div>
    <div class="col-lg-6" ng-if="yearly.length > 0">
        <canvas id="line" class="chart chart-line" chart-data="[yearly]" chart-labels="months" ></canvas>
    </div>
</div>