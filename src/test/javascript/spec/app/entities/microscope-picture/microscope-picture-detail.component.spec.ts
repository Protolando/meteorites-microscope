import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MeteoritesMicroscopeTestModule } from '../../../test.module';
import { MicroscopePictureDetailComponent } from 'app/entities/microscope-picture/microscope-picture-detail.component';
import { MicroscopePicture } from 'app/shared/model/microscope-picture.model';

describe('Component Tests', () => {
  describe('MicroscopePicture Management Detail Component', () => {
    let comp: MicroscopePictureDetailComponent;
    let fixture: ComponentFixture<MicroscopePictureDetailComponent>;
    const route = ({ data: of({ microscopePicture: new MicroscopePicture(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MeteoritesMicroscopeTestModule],
        declarations: [MicroscopePictureDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MicroscopePictureDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MicroscopePictureDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load microscopePicture on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.microscopePicture).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
