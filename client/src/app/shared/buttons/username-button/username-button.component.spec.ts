import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsernameButtonComponent } from './username-button.component';

describe('UsernameButtonComponent', () => {
  let component: UsernameButtonComponent;
  let fixture: ComponentFixture<UsernameButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UsernameButtonComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UsernameButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
