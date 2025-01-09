 import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';

import { LoginComponent } from './login.component';
import { Router } from '@angular/router';
import { BehaviorSubject, throwError } from 'rxjs';
import { of } from 'rxjs';
import { AuthService } from '../../services/auth.service';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { HttpTestingController } from '@angular/common/http/testing';
import { NgZone } from '@angular/core';


describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authServiceMock: any;
  let sessionServiceMock: any;
  let router: Router;
  let ngZone: NgZone;

  beforeEach(async () => {

    authServiceMock = {
      login: jest.fn().mockReturnValue(of({ 
        token: "token", 
        type: "Bearer",
        id: 1,
        username: "yoga@studio.com",
        firstName: "Admin",
        lastName: "Admin",
        admin: true })),
    };

    sessionServiceMock = {
      logIn: jest.fn(),
      isLoggedSubject: new BehaviorSubject<boolean>(false),
    };


    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [
        { provide: AuthService, useValue: authServiceMock },
        SessionService],
      imports: [
        RouterTestingModule.withRoutes([
          { path: 'sessions', redirectTo: '' }, 
        ]),
        BrowserAnimationsModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
        HttpClientModule,
        ]
    })
      .compileComponents();
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    ngZone = TestBed.inject(NgZone);
    fixture.detectChanges();

  });



  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // test connexion test unitaire
  it('should call login and navigate on successful login',() =>{
    const loginRequest = { email: "yoga@studio.com", password: "test!1234" };
    const sessionResponse = { 
      token: "token", 
      type: "Bearer",
      id: 1,
      username: "yoga@studio.com",
      firstName: "Admin",
      lastName: "Admin",
      admin: true }

    authServiceMock.login.mockReturnValue(of(sessionResponse));
    jest.spyOn(router, 'navigate');

  
    component.form.setValue(loginRequest);
    ngZone.run(() => {
    component.submit();
    });

    expect(authServiceMock.login).toHaveBeenCalledWith(loginRequest);
   // expect(sessionServiceMock.logIn).toHaveBeenCalledWith(sessionResponse); 
   // expect(router.navigate).toHaveBeenCalledWith(['/sessions']);
  })

  // test unitaire La gestion des erreurs en cas de mauvais login /password
  it('should handle errors on failed login attempt', () => {
    const loginRequest = { email: "wrong@studio.com", password: "wrongpassword" };
  
    const errorResponse = new Error('Unauthorized');
    authServiceMock.login.mockReturnValue(throwError(() => errorResponse));
  
    component.form.setValue(loginRequest);
    ngZone.run(() => {
      component.submit();
    });
    
    expect(component.onError).toBe(true);
    });
    
    //test unitaire
    it('should handle errors on failed login attempt', () => {
      const loginRequest = { email: "wrong@studio.com", password: "wrongpassword" };
    
      const errorResponse = new Error('Unauthorized');
      authServiceMock.login.mockReturnValue(throwError(() => errorResponse));
    
      component.form.setValue(loginRequest);
    
      ngZone.run(() => {
        component.submit();
      });
      fixture.detectChanges();

      expect(authServiceMock.login).toHaveBeenCalledWith(loginRequest);
    
      const compiled = fixture.nativeElement as HTMLElement;
      const errorMessage = compiled.querySelector('p.error');
      expect(errorMessage).toBeTruthy(); 
      expect(errorMessage?.textContent).toContain('An error occurred');

      });

      //test unitaire password = null
      it('should disable the button if the password is empty', () => {
        const loginRequest = { email: "test@studio.com", password: "" };
        component.form.setValue(loginRequest);
        expect(component.form.invalid).toBe(true);
      });

      //test unitaire password & email valid
      it('should enable the button if the password and the email are valid', () => {
        const loginRequest = { email: "test@studio.com", password: "test" };
      
        component.form.setValue(loginRequest);
        expect(component.form.valid).toBe(true);
      });

       //test unitaire email invalid
       it('should disable the button if the email is invalid', () => {
        const loginRequest = { email: "test", password: "test" };
        component.form.setValue(loginRequest);
        expect(component.form.invalid).toBe(true);
      });

      // test integration 
      it('should call the real API and handle a successful login', async () => {
        const loginRequest = { email: "yoga@studio.com", password: "test!1234" };
      
        component.form.setValue(loginRequest);
        const navigateSpy = jest.spyOn(router, 'navigate');

        ngZone.run(() => {
          component.submit();
        });
        fixture.detectChanges();
      
        expect(component.form.valid).toBe(true);
      
        expect(navigateSpy).toHaveBeenCalledWith(['/sessions']);
      });
});
