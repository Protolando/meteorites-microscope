import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MeteoritesMicroscopeTestModule } from '../../../test.module';
import { MicroscopePictureUpdateComponent } from 'app/entities/microscope-picture/microscope-picture-update.component';
import { MicroscopePictureService } from 'app/entities/microscope-picture/microscope-picture.service';
import { MicroscopePicture } from 'app/shared/model/microscope-picture.model';

describe('Component Tests', () => {
  describe('MicroscopePicture Management Update Component', () => {
    let comp: MicroscopePictureUpdateComponent;
    let fixture: ComponentFixture<MicroscopePictureUpdateComponent>;
    let service: MicroscopePictureService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MeteoritesMicroscopeTestModule],
        declarations: [MicroscopePictureUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MicroscopePictureUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MicroscopePictureUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MicroscopePictureService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MicroscopePicture(123);
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
        const entity = new MicroscopePicture();
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
