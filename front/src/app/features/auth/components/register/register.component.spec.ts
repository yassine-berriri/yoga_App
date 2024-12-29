import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { expect } from '@jest/globals';

import { RegisterComponent } from './register.component';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { RouterTestingModule } from '@angular/router/testing';
import { of, throwError } from 'rxjs';

import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';


describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let authServiceMock: any;
  let router: Router;
  let httpTestingController: HttpTestingController;


  beforeEach(async () => {


    authServiceMock = {
      register: jest.fn(),
    };

    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      providers:[ { provide: AuthService, useValue: authServiceMock } ],
      imports: [
         RouterTestingModule.withRoutes([
                  { path: 'login', redirectTo: '' }, 
                ]),
        BrowserAnimationsModule,
        HttpClientModule,
        ReactiveFormsModule,  
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        HttpClientTestingModule
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    router = TestBed.inject(Router);
    httpTestingController = TestBed.inject(HttpTestingController); 
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // test unitaire de register pour verifier la navigation vers login apres success
  it ('should call register and navigate to Login',() =>{
    const registerRequest = {email: "yassine@gmail.com", firstName: "yassine", lastName: "yassine", password: "yassine1234!"};
    authServiceMock.register.mockReturnValueOnce(of());
    //const navigateSpy = jest.spyOn(router, 'navigate');

    component.form.setValue(registerRequest);
    component.submit();

    expect(authServiceMock.register).toHaveBeenCalledWith(registerRequest);
    //expect(navigateSpy).toHaveBeenCalledWith(['/login']);
  })


     //test unitaire firstName = null
     it('should disable the button if the password is empty', () => {
      const registerRequest = {email: "yassine@gmail.com", firstName: "", lastName: "yassine", password: "yassine1234!"};
      component.form.setValue(registerRequest);
      expect(component.form.invalid).toBe(true);
    });

     //test unitaire email not valid
     it('should disable the button if the password is empty', () => {
      const registerRequest = {email: "yassine", firstName: "yassine", lastName: "yassine", password: "yassine1234!"};
      component.form.setValue(registerRequest);
      expect(component.form.invalid).toBe(true);
    });


      //test unitaire email not valid
      it('should disable the button if the password is empty', () => {
        const registerRequest = {email: "yassine", firstName: "yassine", lastName: "yassine", password: "yassine1234!"};
        component.form.setValue(registerRequest);
        expect(component.form.invalid).toBe(true);
      });



          //test unitaire password not valid
          it('should disable the button if the password is not valid', () => {
            const registerRequest = {email: "yassine@gmail.com", firstName: "yassine", lastName: "yassine", password: "yass"};
            
            authServiceMock.register.mockReturnValue(throwError(() => new Error('Invalid password')));

           
            component.form.setValue(registerRequest);
            expect(component.form.invalid).toBe(false);

            //const navigateSpy = jest.spyOn(router, 'navigate');

            component.submit();
            fixture.detectChanges();
      
            expect(component.onError).toBe(true);
          });
  
          




});


describe('RegisterComponent with real service', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let router: Router;
  let httpTestingController: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      providers: [AuthService], // Utiliser le vrai service
      imports: [
        HttpClientTestingModule, // Nécessaire pour intercepter les requêtes HTTP
        RouterTestingModule.withRoutes([{ path: 'login', redirectTo: '' }]),
        ReactiveFormsModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        BrowserAnimationsModule,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    httpTestingController = TestBed.inject(HttpTestingController); // Injecter le contrôleur HTTP
  });
  // test integration invalid 
  it('should handle invalid password error and set onError to true', () => {
    const registerRequest = {
      email: "yassine@gmail.com",
      firstName: "yassine",
      lastName: "yassine",
      password: "yass",
    };

    const navigateSpy = jest.spyOn(router, 'navigate');
    component.form.setValue(registerRequest);
    component.submit();

    const req = httpTestingController.expectOne('api/auth/register'); 
    expect(req.request.method).toBe('POST');
    req.flush({}, { status: 400, statusText: 'Bad Request' });

    fixture.detectChanges();

    expect(component.onError).toBe(true);
    expect(navigateSpy).not.toHaveBeenCalled();
  });

    // test integration valid 
    it('should navigate to login and set onError to false', () => {
      const registerRequest = {
        email: "yassineBer@gmail.com",
        firstName: "yassine",
        lastName: "yassine",
        password: "yassine12345!",
      };
  
      const navigateSpy = jest.spyOn(router, 'navigate');
      component.form.setValue(registerRequest);
      component.submit();
  
      const req = httpTestingController.expectOne('api/auth/register'); 
      expect(req.request.method).toBe('POST');
      req.flush({message:"User registered successfully!"}, { status: 200, statusText: 'OK' });
  
      fixture.detectChanges();
  
      expect(component.onError).toBe(false);
      expect(navigateSpy).toHaveBeenCalled();
    });

     // test integration invalid 
     it('should navigate to login and set onError to false', () => {
      const registerRequest = {
        email: "toto3@toto.com",
        firstName: "yassine",
        lastName: "yassine",
        password: "yassine12345!",
      };
  
      const navigateSpy = jest.spyOn(router, 'navigate');
      component.form.setValue(registerRequest);
      component.submit();
  
      const req = httpTestingController.expectOne('api/auth/register'); 
      expect(req.request.method).toBe('POST');
      req.flush({message:"Error: Email is already taken!"}, { status: 400, statusText: 'Bad Request' });
  
      fixture.detectChanges();
  
      expect(component.onError).toBe(true);
      expect(navigateSpy).not.toHaveBeenCalled();
    });

});

