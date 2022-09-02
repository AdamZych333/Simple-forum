import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersFollowsComponent } from './users-follows.component';

describe('UsersFollowsComponent', () => {
  let component: UsersFollowsComponent;
  let fixture: ComponentFixture<UsersFollowsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UsersFollowsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UsersFollowsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
