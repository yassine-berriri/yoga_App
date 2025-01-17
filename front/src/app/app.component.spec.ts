import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';

import { AppComponent } from './app.component';
import { Router } from '@angular/router';
import { SessionService } from './services/session.service';
import { of } from 'rxjs';

describe('AppComponent', () => {

  let router: Router;

  const sessionServiceMock = {
    $isLogged: jest.fn(),
    logOut: jest.fn()
  }


  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatToolbarModule
      ],
      declarations: [
        AppComponent
      ],
      providers: [
        { provide: SessionService, useValue: sessionServiceMock } ]
    }).compileComponents();

    router = TestBed.inject(Router);

  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  // unit test should logout
  it('should logout', () => {
    
    const navigateSpy = jest.spyOn(router, 'navigate');
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    app.logout();
    
    expect(navigateSpy).toHaveBeenCalledWith(['']);
  });

  it('$isLogged returns false when the user has not been authenticated', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    sessionServiceMock.$isLogged.mockReturnValueOnce(of(false));
    
    app.$isLogged().subscribe((logged: boolean) => {
      expect(sessionServiceMock.$isLogged).toHaveBeenCalled();
      expect(logged).toEqual(false);
    });
  });

});
