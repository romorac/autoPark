import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VariablesDetailComponent } from './variables-detail.component';

describe('Component Tests', () => {
  describe('Variables Management Detail Component', () => {
    let comp: VariablesDetailComponent;
    let fixture: ComponentFixture<VariablesDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [VariablesDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ variables: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(VariablesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VariablesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load variables on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.variables).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
