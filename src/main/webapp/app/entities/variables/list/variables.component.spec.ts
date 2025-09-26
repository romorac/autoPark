import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { VariablesService } from '../service/variables.service';

import { VariablesComponent } from './variables.component';

describe('Component Tests', () => {
  describe('Variables Management Component', () => {
    let comp: VariablesComponent;
    let fixture: ComponentFixture<VariablesComponent>;
    let service: VariablesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VariablesComponent],
      })
        .overrideTemplate(VariablesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VariablesComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(VariablesService);

      const headers = new HttpHeaders().append('link', 'link;link');
      jest.spyOn(service, 'query').mockReturnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should call load all on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.variables?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
