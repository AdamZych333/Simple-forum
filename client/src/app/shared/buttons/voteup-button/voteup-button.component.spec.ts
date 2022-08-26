import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VoteupButtonComponent } from './voteup-button.component';

describe('VoteupButtonComponent', () => {
  let component: VoteupButtonComponent;
  let fixture: ComponentFixture<VoteupButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VoteupButtonComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VoteupButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
