import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TestComponentComponent } from './test-component/test-component.component';
import { HttpClientModule } from '@angular/common/http';
import { Geolocation } from '@awesome-cordova-plugins/geolocation/ngx';
import { SosTestComponent } from './components/sos-test/sos-test.component';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { MatSliderModule } from '@angular/material/slider';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
// Material Navigation
import { MatMenuModule } from '@angular/material/menu';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
// Material Layout
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatListModule } from '@angular/material/list';
import { MatStepperModule } from '@angular/material/stepper';
import { MatTabsModule } from '@angular/material/tabs';
import { MatTreeModule } from '@angular/material/tree';
// Material Buttons & Indicators
import { MatButtonModule } from '@angular/material/button';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatBadgeModule } from '@angular/material/badge';
import { MatChipsModule } from '@angular/material/chips';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatRippleModule } from '@angular/material/core';
// Material Popups & Modals
import { MatBottomSheetModule } from '@angular/material/bottom-sheet';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSnackBarModule, MAT_SNACK_BAR_DEFAULT_OPTIONS } from '@angular/material/snack-bar';
import { MatTooltipModule } from '@angular/material/tooltip';
// Material Data tables
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UserManagementService } from './services/user-management/user-management.service';
import { HomePageComponent } from './components/home-page/home-page/home-page.component';
import { NgxMatSelectSearchModule } from 'ngx-mat-select-search';
import { NgSelectModule } from '@ng-select/ng-select';
import { SosComponentComponent } from './components/sos-component/sos-component.component';
import { SosManagementService } from './services/sos-management/sos-management.service';
import { NearbySosAlertsComponent } from './components/nearby-sos-alerts/nearby-sos-alerts.component';


const materialModules =  [
MatAutocompleteModule,
MatCheckboxModule,
MatDatepickerModule,
MatFormFieldModule,
MatInputModule,
MatRadioModule,
MatSelectModule,
MatSliderModule,
MatSlideToggleModule,
MatMenuModule,
MatSidenavModule,
MatToolbarModule,
MatCardModule,
MatDividerModule,
MatExpansionModule,
MatGridListModule,
MatListModule,
MatStepperModule,
MatTabsModule,
MatTreeModule,
MatButtonModule,
MatButtonToggleModule,
MatBadgeModule,
MatChipsModule,
MatIconModule,
MatProgressSpinnerModule,
MatProgressBarModule,
MatRippleModule,
MatBottomSheetModule,
MatDialogModule,
MatSnackBarModule,
MatTooltipModule,
MatPaginatorModule,
MatSortModule,
MatTableModule,
NgxMatSelectSearchModule,
NgSelectModule,
ReactiveFormsModule
]

@NgModule({
  declarations: [
    AppComponent,
    TestComponentComponent,
    SosTestComponent,
    LandingPageComponent,
    HomePageComponent,
    SosComponentComponent,
    NearbySosAlertsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ...materialModules,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
  ],
  exports: [
    ...materialModules
  ],
  providers: [
    Geolocation,
    UserManagementService,
    SosManagementService,
    {provide: MAT_SNACK_BAR_DEFAULT_OPTIONS, useValue: {duration: 2500}}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
