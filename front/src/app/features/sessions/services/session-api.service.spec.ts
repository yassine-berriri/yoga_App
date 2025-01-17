import { HttpClientModule, HttpResponse } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { SessionApiService } from './session-api.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { User } from 'src/app/interfaces/user.interface';
import { Session } from '../interfaces/session.interface';

describe('SessionsService', () => {
  let service: SessionApiService;
  let httpTestingController: HttpTestingController;

  const mockEmptyOkResponse: HttpResponse<any> = new HttpResponse({body: of({}), status: 200 });

  const mockUser: User = {
    id: 1,
    email: 'yassine@gmail.com',
    lastName: 'yassine',
    firstName: 'br',
    admin: true,
    password: 'yassine',
    createdAt: new Date()
  };
  
  const mockSession: Session = {
    id: 1,
    name: 'Session',
    description: 'Session description',
    date: new Date(),
    teacher_id: 1,
    users: [mockUser.id]
  };

  const mockAllSessions: Session[] = [mockSession];


  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[
        HttpClientModule,
        HttpClientTestingModule
      ]
    });
    service = TestBed.inject(SessionApiService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  // integration test should delete a session
  it('should delete a session', ()=> {
    service.delete('1').subscribe((response: HttpResponse<any>) => {
      expect(response.ok).toBe(true);
    });

    const req = httpTestingController.expectOne({
      method: 'DELETE',
      url: 'api/session/1'
    });

    req.flush({mockEmptyOkResponse});

  });

  // integration test should create session
  it('create should return a response body containing the session created', () => {
    service.create(mockSession).subscribe((session: Session) => {
      expect(session).toBe(mockSession);
    });

    const req = httpTestingController.expectOne({
      method: 'POST',
      url : 'api/session'
    });

    req.flush({
      mockSession
    });
  });

  // integration test should update session
  it('update should return a response body containing the session updated', () => {
    service.update('1', mockSession).subscribe((session: Session) => {
      expect(session).toBe(mockSession);
    });

    const req = httpTestingController.expectOne({
      method: 'PUT',
      url : 'api/session/1'
    });

    req.flush({
      mockSession
    });
  });

  it('participate should not throw any error when user participate', () => {
    service.participate('1', '1').subscribe(() => {});

    httpTestingController.expectOne({
      method: 'POST',
      url : 'api/session/1/participate/1'
    });
  });

  it('unParticipate should not throw any error when user unParticipate', () => {
    service.unParticipate('1', '1').subscribe(() => {});

    httpTestingController.expectOne({
      method: 'DELETE',
      url : 'api/session/1/participate/1'
    });
  });

  // integration test should return sessions
  it('all should return all existing sessions', () => {
    service.all().subscribe((sessions: Session[]) => {
      expect(sessions).toEqual([mockSession]);
    });

    const req = httpTestingController.expectOne({
      method: 'GET',
      url : 'api/session'
    });

    req.flush({
      mockAllSessions
    });
  });


});
