jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { VariablesService } from '../service/variables.service';
import { IVariables, Variables } from '../variables.model';

import { VariablesUpdateComponent } from './variables-update.component';

describe('Component Tests', () => {
  describe('Variables Management Update Component', () => {
    let comp: VariablesUpdateComponent;
    let fixture: ComponentFixture<VariablesUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let variablesService: VariablesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VariablesUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(VariablesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VariablesUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      variablesService = TestBed.inject(VariablesService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const variables: IVariables = { id: 456 };

        activatedRoute.data = of({ variables });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(variables));
      });
    });

    describe('save', () => {
      /*
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Variables>>();
        const variables = { id: 123 };
        jest.spyOn(variablesService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ variables });
        comp.ngOnInit();
        
        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: variables }));
        saveSubject.complete();
        
        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(variablesService.update).toHaveBeenCalledWith(variables);
        expect(comp.isSaving).toEqual(false);
      });
      
      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Variables>>();
        const variables = new Variables();
        jest.spyOn(variablesService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ variables });
        comp.ngOnInit();
        
        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: variables }));
        saveSubject.complete();
        
        // THEN
        expect(variablesService.create).toHaveBeenCalledWith(variables);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });
      
      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Variables>>();
        const variables = { id: 123 };
        jest.spyOn(variablesService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ variables });
        comp.ngOnInit();
        
        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');
        
        // THEN
        expect(variablesService.update).toHaveBeenCalledWith(variables);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
      */
    });
  });
});
