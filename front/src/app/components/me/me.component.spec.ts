import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, flush, TestBed, tick } from '@angular/core/testing';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { SessionService } from 'src/app/services/session.service';
import { NgZone } from '@angular/core';
import { of } from 'rxjs';


import { MeComponent } from './me.component';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';

describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;
  let ngZone: NgZone;
  let router: Router;

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1
    }
  }

  const mockUser = {
    id: 1,
    name: 'test',
    email: 'test@gmail.com'
  }

  const mockUserService = {
    getById: jest.fn().mockReturnValue(of({mockUser})),
    delete: jest.fn().mockReturnValue(of({}))
  }



  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MeComponent],
      imports: [
        MatSnackBarModule,
        HttpClientModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule
      ],
      providers: [{ provide: SessionService, useValue: mockSessionService },
        { provide: UserService, useValue: mockUserService }
      ],
    })
      .compileComponents();

    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    ngZone = TestBed.inject(NgZone);
    router = TestBed.inject(Router);

  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // unit test data should be retrieved
  it('should get user data', () => {
    ngZone.run(() => {
      component.ngOnInit();
    });
    
    expect(component.user).toBeDefined();
  });

  // unit test should delete user
  it('should delete user', () => {
    mockSessionService.sessionInformation.admin = false;
    
    ngZone.run(() => {
      component.delete();
    });

    expect(mockUserService.delete).toHaveBeenCalledWith('1');
  });

});
