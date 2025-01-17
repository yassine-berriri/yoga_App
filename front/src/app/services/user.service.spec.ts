import { HttpClientModule, HttpResponse } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { UserService } from './user.service';
import { User } from '../interfaces/user.interface';
import { of } from 'rxjs';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

const path: string = 'api/user';

const mockUser: User = {
  id: 1,
  email: 'yassine@gmail.com',
  lastName: 'yassine',
  firstName: 'yassine',
  admin: false,
  password: 'yassine1234!',
  createdAt: new Date()
}

const mockEmptyOkResponse: HttpResponse<any> = new HttpResponse({body: of({}), status: 200 });




describe('UserService', () => {
  let service: UserService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[
        HttpClientModule,
        HttpClientTestingModule
      ]
    });
    service = TestBed.inject(UserService);
    httpTestingController = TestBed.inject(HttpTestingController);

  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  // integration test for getById
  it('getById should return user', () => {
    service.getById('1').subscribe((user: User) => {
      expect(user).toEqual(mockUser);
    });

    const req = httpTestingController.expectOne({
      method: 'GET',
      url : path + `/1`
    });

    req.flush({
      mockUser
    });
  });

  // integration test for delete
  it('delete should return a successful response', () => {
    service.delete('1').subscribe((response: HttpResponse<any>) => {
      expect(response.ok).toBe(true);
    });

    const req = httpTestingController.expectOne({
      method: 'DELETE',
      url : path + `/1`
    });

    req.flush({
      mockEmptyOkResponse
    });
  });

});
