<div class="category-block col-md-12">
    <h1>Liste des cat&eacute;gories</h1>
    <ul class="col-md-6">
        <li class="entry" ng-repeat="category in categories | orderBy:'label'" ng-click="selectCategory(category)">{{$index + 1 + '. ' + category.label}} ({{category.values.length}} valeurs)<#if authorities?seq_contains("ADMIN")><button type="button" ng-click="removeCategory(category);$event.stopPropagation()" class="btn btn-danger">Supprimer</button></#if></li>
        <#if authorities?seq_contains("ADMIN")>
            <li class="adder"><input class="form-control col-md-8" type="text" ng-model="ctx.categoryNewLabel" placeholder="Ajouter une categorie"/> <button type="button" ng-disabled="ctx.categoryNewLabel.length == 0" ng-click="addNewCategory()" class="btn btn-primary col-md-4">Ajouter</button></li>
        </#if>
    </ul>
    <div class="col-md-6" ng-if="selectedCategory.label">
        <h3>Cat&eacute;gorie : {{selectedCategory.label}}</h3>
        <ul>
            <li class="entry" ng-repeat="value in selectedCategory.values">
                <div>
                    <#if !authorities?seq_contains("ADMIN")>
                        <input type="text" ng-model="selectedCategory.values[$index]" style="width: 60%" disabled="disabled"/>
                    </#if>
                    <#if authorities?seq_contains("ADMIN")>
                        <input type="text" ng-model="selectedCategory.values[$index]" style="width: 60%"/>
                        <button type="button" ng-click="removeCategoryValue($index)" class="btn btn-danger">Supprimer</button>
                        <button type="button" ng-click="updateCategory()" class="btn btn-primary">Corriger label</button>
                    </#if>
                </div>
            </li>
            <#if authorities?seq_contains("ADMIN")>
                <li class="adder"><input class="form-control col-md-8" type="text" ng-model="ctx.categoryNewValue" placeholder="Ajouter une valeur"/> <button type="button" ng-disabled="ctx.categoryNewValue.length == 0" ng-click="addNewCategoryValue()" class="btn btn-primary col-md-4">Ajouter</button></li>
            </#if>
        </ul>
    </div>
</div>