import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MeteoritesMicroscopeTestModule } from '../../../test.module';
import { RockUpdateComponent } from 'app/entities/rock/rock-update.component';
import { RockService } from 'app/entities/rock/rock.service';
import { Rock } from 'app/shared/model/rock.model';

describe('Component Tests', () => {
  describe('Rock Management Update Component', () => {
    let comp: RockUpdateComponent;
    let fixture: ComponentFixture<RockUpdateComponent>;
    let service: RockService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MeteoritesMicroscopeTestModule],
        declarations: [RockUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RockUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RockUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RockService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Rock(123);
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
        const entity = new Rock();
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
