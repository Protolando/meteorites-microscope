import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MeteoritesMicroscopeTestModule } from '../../../test.module';
import { MicroscopeDetailComponent } from 'app/entities/microscope/microscope-detail.component';
import { Microscope } from 'app/shared/model/microscope.model';

describe('Component Tests', () => {
  describe('Microscope Management Detail Component', () => {
    let comp: MicroscopeDetailComponent;
    let fixture: ComponentFixture<MicroscopeDetailComponent>;
    const route = ({ data: of({ microscope: new Microscope(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MeteoritesMicroscopeTestModule],
        declarations: [MicroscopeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MicroscopeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MicroscopeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load microscope on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.microscope).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
