import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { TeacherService } from './teacher.service';
import { Teacher } from '../interfaces/teacher.interface';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('TeacherService', () => {
  let service: TeacherService;
  let httpTestingController: HttpTestingController;

  const path: string = 'api/teacher';
  
  const mockTeacher: Teacher = {
    id: 1,
    lastName: 'yassine',
    firstName: 'yassine',
    createdAt: new Date(),
    updatedAt: new Date()
  };
  
  const mockListTeachers: Teacher[] = [mockTeacher];

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[
        HttpClientTestingModule
      ]
    });
    service = TestBed.inject(TeacherService);
    httpTestingController = TestBed.inject(HttpTestingController);

  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  // integration test for get all
  it('all should return all existing teachers', () => {
    service.all().subscribe((teachers: Teacher[]) => {
      expect(teachers).toEqual([mockTeacher]);
    });

    const req = httpTestingController.expectOne({
      method: 'GET',
      url : path
    });

    req.flush({
      mockListTeachers
    });
  });

  // integration test for get by id
  it('detail should return the teacher', () => {
    service.detail('1').subscribe((teacher: Teacher) => {
      expect(teacher).toEqual(mockTeacher);
    });

    const req = httpTestingController.expectOne({
      method: 'GET',
      url : path + `/1`
    });

    req.flush({
      mockTeacher
    });
  });

});
