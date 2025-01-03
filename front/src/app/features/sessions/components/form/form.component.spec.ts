import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import {  ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';
import { SessionApiService } from '../../services/session-api.service';

import { FormComponent } from './form.component';
import { data } from 'cypress/types/jquery';
import { of } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { NgZone } from '@angular/core';
import { Session } from '../../interfaces/session.interface';

describe('FormComponent', () => {
  let component: FormComponent;
  let fixture: ComponentFixture<FormComponent>;
  let snackBarMock: jest.Mocked<MatSnackBar>;
  let router: Router;
  let ngZone: NgZone;

  const mockSessionService = {
    sessionInformation: {
      admin: true
    }
  }

  const mockSession: Session = {
    id: 1,
    name: 'test',
    description: 'test session',
    date: new Date(),
    teacher_id: 1,
    users: []
  }
  
  const mockSessionApiService = {
    create: jest.fn().mockReturnValue(of(mockSession)),
    detail: jest.fn().mockReturnValue(of(mockSession)),
    update: jest.fn().mockReturnValue(of(mockSession))
  };

  beforeEach(async () => {

    snackBarMock = {
      open: jest.fn(),
    } as unknown as jest.Mocked<MatSnackBar>;

   

    await TestBed.configureTestingModule({

      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule, 
        MatSnackBarModule,
        MatSelectModule,
        BrowserAnimationsModule
      ],
      providers: [ { 
        provide: MatSnackBar, useValue: snackBarMock },
        { provide: SessionService, useValue: mockSessionService },
        {provide: SessionApiService, useValue: mockSessionApiService}
      ],
      declarations: [FormComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(FormComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    ngZone = TestBed.inject(NgZone);
    fixture.detectChanges();
  });



  it('should create', () => {
    expect(component).toBeTruthy();
  });


  // test unitaire should create session
  it('should create session', () => {
    const sessionForm = {name: 'Yoga Basics', date: '2024-01-01', teacher_id: 1,  description: 'Learn the basics of Yoga.',};

    component.sessionForm?.setValue(sessionForm);

    ngZone.run(() => {
    component.submit();
    });

    expect(component.onUpdate).toBeFalsy();
    //expect(snackBarMock.open).toHaveBeenCalledWith('Session created !', 'Close',  { duration: 3000 });

  });

  // test unitaire display error when form is invalid
  it ("should display error when form is invalid", () => {
    component.sessionForm?.setValue({name: '', date: '', teacher_id: '', description: ''});
    fixture.detectChanges();

    const form = component.sessionForm;

    if (form){
      expect(form.invalid).toBeTruthy();
      expect(form.controls['name'].hasError('required')).toBeTruthy();
      expect(form.controls['date'].hasError('required')).toBeTruthy();
      expect(form.controls['teacher_id'].hasError('required')).toBeTruthy();
      expect(form.controls['description'].hasError('required')).toBeTruthy();
    }

  });

  

});
