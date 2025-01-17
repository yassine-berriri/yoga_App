import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatSnackBarModule, MatSnackBar } from '@angular/material/snack-bar';
import { RouterTestingModule, } from '@angular/router/testing';
import { expect } from '@jest/globals'; 
import { SessionService } from '../../../../services/session.service';

import { DetailComponent } from './detail.component';
import { SessionApiService } from '../../services/session-api.service';
import { of } from 'rxjs';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { NgZone } from '@angular/core';
import { Session } from '../../interfaces/session.interface';
import { Teacher } from 'src/app/interfaces/teacher.interface';


describe('DetailComponent', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>; 
  let service: SessionService;
  let snackBarMock: jest.Mocked<MatSnackBar>;
  let ngZone: NgZone;
  let sessionApiService: SessionApiService;

  

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1
    }
  }

  const mockSessionApiService = {
    delete: jest.fn().mockReturnValue(of({})),
    detail: jest.fn().mockReturnValue(of({
      id: 1,
      name: 'Yoga Basics',
      date: '2024-01-01',
      description: 'Learn the basics of Yoga.',
      users: [1, 2]
    })),
  };

  const mockSessionWithUser: Session = {
    id: 1,
    name: 'session',
    description: 'session',
    date: new Date(),
    teacher_id: 1,
    users: [1]
  }


  beforeEach(async () => {

    snackBarMock = {
      open: jest.fn(),
    } as unknown as jest.Mocked<MatSnackBar>;


    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatSnackBarModule,
        ReactiveFormsModule,
        MatCardModule,
        MatIconModule,
      ],
      declarations: [DetailComponent], 
      providers: [
        { provide: MatSnackBar, useValue: snackBarMock },
        { provide: SessionService, useValue: mockSessionService },
        { provide: SessionApiService, useValue: mockSessionApiService },
      ],
    })
      .compileComponents();
      service = TestBed.inject(SessionService);
    fixture = TestBed.createComponent(DetailComponent);
    component = fixture.componentInstance;
    ngZone = TestBed.inject(NgZone);
    fixture.detectChanges();
    sessionApiService = TestBed.inject(SessionApiService );

  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // test unitaire Suppression d’une session
  it('should delete a session', () => {
    component.sessionId = '1';
    ngZone.run(() => {
      component.delete();
    });
    expect(snackBarMock.open).toHaveBeenCalledWith('Session deleted !', 'Close',  { duration: 3000 });
  });


it('ngOnInit() should fetch session with user and update component properties', () => {
    const exitPageSpy = jest.spyOn(component as any, 'fetchSession');
    jest.spyOn(sessionApiService, 'detail').mockReturnValue(of(mockSessionWithUser));

    component.ngOnInit();

    expect(exitPageSpy).toBeCalledTimes(1);
    expect(component.session).toEqual(mockSessionWithUser);
    expect(component.isParticipate).toEqual(true);
  });

 // unit test
  it('back should call window.history.back', () => {
    const windowHistoryBackSpy = jest.spyOn(window.history, 'back');

    component.back();

    expect(windowHistoryBackSpy).toBeCalledTimes(1);
  });


});

