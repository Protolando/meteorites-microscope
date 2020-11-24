import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MeteoritesMicroscopeTestModule } from '../../../test.module';
import { MicroscopeUpdateComponent } from 'app/entities/microscope/microscope-update.component';
import { MicroscopeService } from 'app/entities/microscope/microscope.service';
import { Microscope } from 'app/shared/model/microscope.model';

describe('Component Tests', () => {
  describe('Microscope Management Update Component', () => {
    let comp: MicroscopeUpdateComponent;
    let fixture: ComponentFixture<MicroscopeUpdateComponent>;
    let service: MicroscopeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MeteoritesMicroscopeTestModule],
        declarations: [MicroscopeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MicroscopeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MicroscopeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MicroscopeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Microscope(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Microscope();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
